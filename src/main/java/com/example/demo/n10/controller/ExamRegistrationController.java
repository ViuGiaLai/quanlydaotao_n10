package com.example.demo.n10.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.example.demo.n10.model.entity.ExamRegistration;
import com.example.demo.n10.service.ExamRegistrationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/exam-registrations")
@CrossOrigin("*")
public class ExamRegistrationController {
    
    private static final Logger logger = LoggerFactory.getLogger(ExamRegistrationController.class);
    private final ExamRegistrationService examRegistrationService;

    public ExamRegistrationController(ExamRegistrationService examRegistrationService) {
        this.examRegistrationService = examRegistrationService;
    }

    // Lấy tất cả
    @GetMapping
    public List<ExamRegistration> getAllExamRegistrations() {
        return examRegistrationService.findAll();
    }

    // Lấy tất cả (alias)
    @GetMapping("/list")
    public List<ExamRegistration> listAllExamRegistrations() {
        return examRegistrationService.findAll();
    }

    // Chi tiết theo id
    @GetMapping("/{id}")
    public ExamRegistration getExamRegistrationById(@PathVariable UUID id) {
        return examRegistrationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam Registration not found with id: " + id));
    }

    // Lọc theo examRoomId - lấy danh sách thí sinh trong 1 phòng thi
    @GetMapping("/by-room/{examRoomId}")
    public List<ExamRegistration> getByExamRoomId(@PathVariable String examRoomId) {
        return examRegistrationService.findByExamRoomId(examRoomId);
    }

    // Lọc theo examId
    @GetMapping("/by-exam/{examId}")
    public List<ExamRegistration> getByExamId(@PathVariable String examId) {
        return examRegistrationService.findByExamId(examId);
    }

    // Lọc theo examId và examRoomId
    @GetMapping("/by-exam/{examId}/room/{examRoomId}")
    public List<ExamRegistration> getByExamIdAndExamRoomId(
            @PathVariable String examId, 
            @PathVariable String examRoomId) {
        return examRegistrationService.findByExamIdAndExamRoomId(examId, examRoomId);
    }

    // Lọc theo studentId
    @GetMapping("/by-student/{studentId}")
    public List<ExamRegistration> getByStudentId(@PathVariable String studentId) {
        return examRegistrationService.findByStudentId(studentId);
    }

    // Lọc theo isActive
    @GetMapping("/active")
    public List<ExamRegistration> getActive() {
        return examRegistrationService.findByIsActive(true);
    }

    // Tạo mới
    @PostMapping
    public ExamRegistration createExamRegistration(@RequestBody ExamRegistration examRegistration) {
        logger.info("Received examRegistration: examId={}, examRoomId={}, studentId={}, rollNumber={}, isActive={}",
                examRegistration.getExamId(), examRegistration.getExamRoomId(), 
                examRegistration.getStudentId(), examRegistration.getRollNumber(),
                examRegistration.getIsActive());
        return examRegistrationService.save(examRegistration);
    }

    // Cập nhật
    @PutMapping("/{id}")
    public ExamRegistration updateExamRegistration(@PathVariable UUID id, @RequestBody ExamRegistration examRegistration) {
        return examRegistrationService.findById(id)
                .map(existingExamRegistration -> {
                    existingExamRegistration.setExamId(examRegistration.getExamId());
                    existingExamRegistration.setExamRoomId(examRegistration.getExamRoomId());
                    existingExamRegistration.setStudentId(examRegistration.getStudentId());
                    existingExamRegistration.setRollNumber(examRegistration.getRollNumber());
                    existingExamRegistration.setIsActive(examRegistration.getIsActive());
                    return examRegistrationService.save(existingExamRegistration);
                })
                .orElseThrow(() -> new RuntimeException("Exam Registration not found with id: " + id));
    }

    // Xóa
    @DeleteMapping("/{id}")
    public void deleteExamRegistration(@PathVariable UUID id) {
        if (examRegistrationService.findById(id).isPresent()) {
            examRegistrationService.deleteById(id);
        } else {
            throw new RuntimeException("Exam Registration not found with id: " + id);
        }
    }
}
