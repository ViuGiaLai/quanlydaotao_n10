package com.example.demo.n10.controller;

import com.example.demo.n10.model.entity.ExamPaper;
import com.example.demo.n10.service.ExamPaperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/exam-papers")
public class ExamPaperController {

    private final ExamPaperService examPaperService;

    public ExamPaperController(ExamPaperService examPaperService) {
        this.examPaperService = examPaperService;
    }

    @GetMapping
    public List<ExamPaper> getAllExamPapers() {
        return examPaperService.findAll();
    }

    @GetMapping("/list")
    public List<ExamPaper> listAllExamPapers() {
        return examPaperService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamPaper> getExamPaperById(@PathVariable UUID id) {
        return examPaperService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ExamPaper createExamPaper(@RequestBody ExamPaper examPaper) {
        return examPaperService.save(examPaper);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamPaper> updateExamPaper(@PathVariable UUID id, @RequestBody ExamPaper examPaper) {
        return examPaperService.findById(id)
                .map(existingExamPaper -> {
                    existingExamPaper.setExamId(examPaper.getExamId());
                    existingExamPaper.setPaperCode(examPaper.getPaperCode());
                    existingExamPaper.setFileUrl(examPaper.getFileUrl());
                    existingExamPaper.setIsActive(examPaper.getIsActive());
                    // Update other fields as necessary
                    return ResponseEntity.ok(examPaperService.save(existingExamPaper));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamPaper(@PathVariable UUID id) {
        if (examPaperService.findById(id).isPresent()) {
            examPaperService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
