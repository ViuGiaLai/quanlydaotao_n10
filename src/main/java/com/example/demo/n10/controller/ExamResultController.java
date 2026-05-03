package com.example.demo.n10.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.example.demo.n10.model.entity.ExamResult;
import com.example.demo.n10.service.ExamResultService;

@RestController
@RequestMapping("/api/exam-results")
public class ExamResultController {
    
    private final ExamResultService examResultService;
    
    public ExamResultController(ExamResultService examResultService) {
        this.examResultService = examResultService;
    }

    @GetMapping
    public List<ExamResult> getAllExamResults() {
        return examResultService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamResult> getExamResultById(@PathVariable UUID id) {
        return examResultService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ExamResult createExamResult(@RequestBody ExamResult examResult) {
        return examResultService.save(examResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamResult> updateExamResult(@PathVariable UUID id, @RequestBody ExamResult examResult) {
        return examResultService.findById(id)
                .map(existingExamResult -> {
                    existingExamResult.setRegistrationId(examResult.getRegistrationId());
                    existingExamResult.setScore(examResult.getScore());
                    existingExamResult.setStatus(examResult.getStatus());
                    existingExamResult.setIsLocked(examResult.getIsLocked());
                    existingExamResult.setIsActive(examResult.getIsActive());
                    return ResponseEntity.ok(examResultService.save(existingExamResult));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamResult(@PathVariable UUID id) {
        if (examResultService.findById(id).isPresent()) {
            examResultService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
