package com.example.demo.n2.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.n10.model.entity.Exam;
import com.example.demo.n10.model.entity.ExamResult;
import com.example.demo.n10.model.entity.ExamPaper;
import com.example.demo.n10.model.entity.ExamRegistration;
import com.example.demo.n10.service.ExamResultService;
import com.example.demo.n10.service.ExamPaperService;
import com.example.demo.n10.service.ExamService;
import com.example.demo.n10.service.ExamRegistrationService;
import com.example.demo.n10.service.ExamRoomService;
import com.example.demo.n1.model.entity.User;
import com.example.demo.n2.model.entity.Student;
import com.example.demo.n2.model.entity.Teacher;
import com.example.demo.n2.service.StudentService;
import com.example.demo.n2.service.TeacherService;
import com.example.demo.n1.repository.UserRepository;

@Controller
public class TeacherPageController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final ExamService examService;
    private final ExamResultService examResultService;
    private final ExamPaperService examPaperService;
    private final ExamRegistrationService examRegistrationService;
    private final ExamRoomService examRoomService;
    private final UserRepository userRepository;

    public TeacherPageController(TeacherService teacherService, StudentService studentService,
            ExamService examService, ExamResultService examResultService, ExamPaperService examPaperService,
            ExamRegistrationService examRegistrationService, ExamRoomService examRoomService, 
            UserRepository userRepository) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.examService = examService;
        this.examResultService = examResultService;
        this.examPaperService = examPaperService;
        this.examRegistrationService = examRegistrationService;
        this.examRoomService = examRoomService;
        this.userRepository = userRepository;
    }

    @GetMapping("/teacher")
    public String teacherDashboard(Model model, Authentication auth) {
        String username = auth.getName();
        Teacher teacher = null;
        
        // First, find user by username or email
        Optional<User> userOpt = userRepository
                .findFirstByUsernameIgnoreCaseAndDeletedAtIsNull(username)
                .or(() -> userRepository.findFirstByEmailIgnoreCaseAndDeletedAtIsNull(username));
        
        if (userOpt.isPresent()) {
            var user = userOpt.get();
            // Find teacher by user_id
            Optional<Teacher> teacherOpt = teacherService.findByUserId(user.getId());
            if (teacherOpt.isPresent()) {
                teacher = teacherOpt.get();
            }
        }
        
        // Fallback: try to get first teacher from database if not found
        if (teacher == null) {
            List<Teacher> allTeachers = teacherService.findAll();
            if (!allTeachers.isEmpty()) {
                teacher = allTeachers.get(0);
            }
        }
        
        model.addAttribute("teacher", teacher);
        
        // Populate dashboard data if teacher exists
        if (teacher != null) {
            // Count exams
            List<Exam> allExams = examService.getAllExams();
            model.addAttribute("examCount", allExams.size());
            
            // Count students
            List<Student> allStudents = studentService.findAll();
            model.addAttribute("studentCount", allStudents.size());
            
            // Count pending scores to grade
            List<ExamResult> allResults = examResultService.findAll();
            long pendingCount = allResults.stream()
                    .filter(r -> r.getScore() == null)
                    .count();
            model.addAttribute("pendingScoreCount", pendingCount);
            
            // Count exam papers
            List<ExamPaper> allPapers = examPaperService.findAll();
            model.addAttribute("paperCount", allPapers.size());
        } else {
            // Default values when no teacher found
            model.addAttribute("examCount", 0);
            model.addAttribute("studentCount", 0);
            model.addAttribute("pendingScoreCount", 0);
            model.addAttribute("paperCount", 0);
        }
        
        return "teacher/dashboard";
    }

    @GetMapping("/teacher/exams")
    public String teacherExams(Model model, Authentication auth) {
        // Get exams that teacher is responsible for
        List<Exam> exams = examService.getAllExams();
        model.addAttribute("exams", exams);
        
        // Lấy exam types để hiển thị tên
        var examTypes = examService.getAllExamTypes();
        java.util.Map<String, String> examTypeMap = new java.util.HashMap<>();
        for (var et : examTypes) {
            examTypeMap.put(et.getId().toString(), et.getName());
        }
        model.addAttribute("examTypeMap", examTypeMap);
        
        return "teacher/exams";
    }

    private Teacher getCurrentTeacher(Authentication auth) {
        String username = auth.getName();
        
        // First, find user by username or email
        Optional<User> userOpt = userRepository
                .findFirstByUsernameIgnoreCaseAndDeletedAtIsNull(username)
                .or(() -> userRepository.findFirstByEmailIgnoreCaseAndDeletedAtIsNull(username));
        
        if (userOpt.isPresent()) {
            var user = userOpt.get();
            Optional<Teacher> teacherOpt = teacherService.findByUserId(user.getId());
            if (teacherOpt.isPresent()) {
                return teacherOpt.get();
            }
        }
        
        // Fallback
        return teacherService.findAll().stream().findFirst().orElse(null);
    }

    @GetMapping("/teacher/students")
    public String students(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "teacher/students";
    }

    @GetMapping("/teacher/scores")
    public String scores(Model model) {
        List<ExamResult> results = examResultService.findAll();
        model.addAttribute("results", results);
        return "teacher/scores";
    }

    @PostMapping("/teacher/scores/save")
    public String saveScore(@RequestParam String resultId, @RequestParam Double score, 
                        @RequestParam String status) {
        examResultService.findById(UUID.fromString(resultId)).ifPresent(result -> {
            result.setScore(score);
            result.setStatus(status);
            examResultService.save(result);
        });
        return "redirect:/teacher/scores";
    }

    @GetMapping("/teacher/exam-papers")
    public String examPapers(Model model) {
        List<ExamPaper> papers = examPaperService.findAll();
        model.addAttribute("papers", papers);
        
        // Lấy exam types để hiển thị tên
        var examTypes = examService.getAllExamTypes();
        java.util.Map<String, String> examTypeMap = new java.util.HashMap<>();
        for (var et : examTypes) {
            examTypeMap.put(et.getId().toString(), et.getName());
        }
        model.addAttribute("examTypeMap", examTypeMap);
        
        return "teacher/exam-papers";
    }

    @PostMapping("/teacher/exam-papers/upload")
    public String uploadExamPaper(@RequestParam String examId, @RequestParam String paperCode) {
        ExamPaper paper = new ExamPaper();
        paper.setExamId(UUID.fromString(examId));
        paper.setPaperCode(paperCode);
        paper.setIsActive(true);
        examPaperService.save(paper);
        return "redirect:/teacher/exam-papers";
    }

    @GetMapping("/teacher/attendance")
    public String attendance(Model model) {
        // Show attendance for teacher's exams - get all registrations
        List<ExamRegistration> registrations = examRegistrationService.findAll();
        model.addAttribute("registrations", registrations);
        
        // Lấy students và examRooms để hiển thị tên
        var students = studentService.findAll();
        var examRooms = examRoomService.getAllExamRooms();
        
        java.util.Map<String, String> studentMap = new java.util.HashMap<>();
        for (var s : students) {
            studentMap.put(s.getId().toString(), s.getFullname());
        }
        model.addAttribute("studentMap", studentMap);
        
        java.util.Map<String, String> examRoomMap = new java.util.HashMap<>();
        for (var er : examRooms) {
            examRoomMap.put(er.getId().toString(), er.getExamRoomName());
        }
        model.addAttribute("examRoomMap", examRoomMap);
        
        return "teacher/attendance";
    }

    @PostMapping("/teacher/attendance/mark")
    public String markAttendance(@RequestParam String registrationId, @RequestParam Boolean present) {
        // Mark student attendance
        return "redirect:/teacher/attendance";
    }
}