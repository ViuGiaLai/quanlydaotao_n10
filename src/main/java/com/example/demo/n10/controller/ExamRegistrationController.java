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
public class ExamRegistrationController {
    
    private static final Logger logger = LoggerFactory.getLogger(ExamRegistrationController.class);
    private final ExamRegistrationService examRegistrationService;

    public ExamRegistrationController(ExamRegistrationService examRegistrationService) {
        this.examRegistrationService = examRegistrationService;
    }

    @GetMapping
    public List<ExamRegistration> getAllExamRegistrations() {
        return examRegistrationService.findAll();
    }

    @GetMapping("/list")
    public List<ExamRegistration> listAllExamRegistrations() {
        return examRegistrationService.findAll();
    }

    @GetMapping("/{id}")
    public ExamRegistration getExamRegistrationById(@PathVariable UUID id) {
        return examRegistrationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam Registration not found with id: " + id));
    }

    @PostMapping
    public ExamRegistration createExamRegistration(@RequestBody ExamRegistration examRegistration) {
        logger.info("Received examRegistration: examId={}, examRoomId={}, studentId={}, rollNumber={}, isActive={}",
                examRegistration.getExamId(), examRegistration.getExamRoomId(), 
                examRegistration.getStudentId(), examRegistration.getRollNumber(),
                examRegistration.getIsActive());
        return examRegistrationService.save(examRegistration);
    }

    @PutMapping("/{id}")
    public ExamRegistration updateExamRegistration(@PathVariable UUID id, @RequestBody ExamRegistration examRegistration) {
        return examRegistrationService.findById(id)
                .map(existingExamRegistration -> {
                    existingExamRegistration.setExamId(examRegistration.getExamId());
                    existingExamRegistration.setExamRoomId(examRegistration.getExamRoomId());
                    existingExamRegistration.setStudentId(examRegistration.getStudentId());
                    existingExamRegistration.setRollNumber(examRegistration.getRollNumber());
                    existingExamRegistration.setIsActive(examRegistration.getIsActive());
                    // Update other fields as necessary
                    return examRegistrationService.save(existingExamRegistration);
                })
                .orElseThrow(() -> new RuntimeException("Exam Registration not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteExamRegistration(@PathVariable UUID id) {
        if (examRegistrationService.findById(id).isPresent()) {
            examRegistrationService.deleteById(id);
        } else {
            throw new RuntimeException("Exam Registration not found with id: " + id);
        }
    }
}
