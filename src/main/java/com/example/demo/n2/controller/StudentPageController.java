package com.example.demo.n2.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.n10.model.entity.Exam;
import com.example.demo.n10.model.entity.ExamRegistration;
import com.example.demo.n10.model.entity.ExamResult;
import com.example.demo.n10.service.ExamRegistrationService;
import com.example.demo.n10.service.ExamResultService;
import com.example.demo.n10.service.ExamService;
import com.example.demo.n1.model.entity.User;
import com.example.demo.n2.model.entity.Student;
import com.example.demo.n2.service.StudentService;
import com.example.demo.n1.repository.UserRepository;

@Controller
public class StudentPageController {

    private final StudentService studentService;
    private final ExamService examService;
    private final ExamRegistrationService examRegistrationService;
    private final ExamResultService examResultService;
    private final UserRepository userRepository;

    public StudentPageController(StudentService studentService, ExamService examService,
            ExamRegistrationService examRegistrationService, ExamResultService examResultService,
            UserRepository userRepository) {
        this.studentService = studentService;
        this.examService = examService;
        this.examRegistrationService = examRegistrationService;
        this.examResultService = examResultService;
        this.userRepository = userRepository;
    }

    @GetMapping("/student")
    public String studentDashboard(Model model, Authentication auth) {
        String username = auth.getName();
        Student student = null;
        
        // First, find user by username or email
        Optional<com.example.demo.n1.model.entity.User> userOpt = userRepository
                .findFirstByUsernameIgnoreCaseAndDeletedAtIsNull(username)
                .or(() -> userRepository.findFirstByEmailIgnoreCaseAndDeletedAtIsNull(username));
        
        if (userOpt.isPresent()) {
            var user = userOpt.get();
            // Find student by user_id
            Optional<Student> studentOpt = studentService.findByUserId(user.getId());
            if (studentOpt.isPresent()) {
                student = studentOpt.get();
            }
        }
        
        // Fallback: try to get first student from database if not found
        if (student == null) {
            List<Student> allStudents = studentService.findAll();
            if (!allStudents.isEmpty()) {
                student = allStudents.get(0);
            }
        }
        
        model.addAttribute("student", student);
        
        // Populate dashboard data if student exists
        if (student != null) {
            String studentId = student.getId().toString();
            
            // Count upcoming exams
            List<Exam> allExams = examService.getAllExams();
            long upcomingCount = allExams.stream()
                    .filter(e -> e.getExamDate() != null && e.getExamDate().isAfter(java.time.LocalDate.now()))
                    .count();
            model.addAttribute("upcomingExamCount", upcomingCount);
            
            // Count registered exams
            List<ExamRegistration> allRegs = examRegistrationService.findAll();
            long registeredCount = allRegs.stream()
                    .filter(r -> studentId.equals(r.getStudentId()) && Boolean.TRUE.equals(r.getIsActive()))
                    .count();
            model.addAttribute("registeredCount", registeredCount);
            
            // Calculate average score
            List<ExamResult> allResults = examResultService.findAll();
            double avgScore = allResults.stream()
                    .filter(r -> studentId.equals(r.getRegistrationId().toString()) && r.getScore() != null)
                    .mapToDouble(ExamResult::getScore)
                    .average()
                    .orElse(0.0);
            model.addAttribute("avgScore", avgScore > 0 ? String.format("%.1f", avgScore) : "-");
            
            // Notifications (empty list for now)
            model.addAttribute("notifications", Collections.emptyList());
        } else {
            // Default values when no student found
            model.addAttribute("upcomingExamCount", 0);
            model.addAttribute("registeredCount", 0);
            model.addAttribute("avgScore", "-");
            model.addAttribute("notifications", Collections.emptyList());
        }
        
        return "student/dashboard";
    }

    @GetMapping("/student/exams")
    public String studentExams(Model model, Authentication auth) {
        List<Exam> exams = examService.getAllExams();
        model.addAttribute("exams", exams);
        return "student/exams";
    }

    private Student getCurrentStudent(Authentication auth) {
        String username = auth.getName();
        
        // First, find user by username or email
        Optional<User> userOpt = userRepository
                .findFirstByUsernameIgnoreCaseAndDeletedAtIsNull(username)
                .or(() -> userRepository.findFirstByEmailIgnoreCaseAndDeletedAtIsNull(username));
        
        if (userOpt.isPresent()) {
            var user = userOpt.get();
            Optional<Student> studentOpt = studentService.findByUserId(user.getId());
            if (studentOpt.isPresent()) {
                return studentOpt.get();
            }
        }
        
        // Fallback
        return studentService.findAll().stream().findFirst().orElse(null);
    }

    @GetMapping("/student/my-registrations")
    public String myRegistrations(Model model, Authentication auth) {
        Student student = getCurrentStudent(auth);
        if (student != null) {
            List<ExamRegistration> registrations = examRegistrationService.findAll().stream()
                    .filter(r -> student.getId().toString().equals(r.getStudentId()))
                    .toList();
            model.addAttribute("registrations", registrations);
        }
        return "student/registrations";
    }

    @GetMapping("/student/results")
    public String myResults(Model model, Authentication auth) {
        Student student = getCurrentStudent(auth);
        if (student != null) {
            List<ExamResult> results = examResultService.findAll().stream()
                    .filter(r -> student.getId().toString().equals(r.getRegistrationId().toString()))
                    .toList();
            model.addAttribute("results", results);
        }
        return "student/results";
    }

    @GetMapping("/student/register/{examId}")
    public String registerExam(@PathVariable String examId, Authentication auth) {
        Student student = getCurrentStudent(auth);
        if (student != null) {
            ExamRegistration reg = new ExamRegistration();
            reg.setExamId(examId);
            reg.setStudentId(student.getId().toString());
            reg.setRollNumber("REG-" + System.currentTimeMillis());
            reg.setIsActive(true);
            examRegistrationService.save(reg);
        }
        return "redirect:/student/my-registrations";
    }

    @GetMapping("/student/cancel/{registrationId}")
    public String cancelRegistration(@PathVariable UUID registrationId) {
        examRegistrationService.deleteById(registrationId);
        return "redirect:/student/my-registrations";
    }
}