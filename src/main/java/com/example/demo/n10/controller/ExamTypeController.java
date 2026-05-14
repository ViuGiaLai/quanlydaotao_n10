package com.example.demo.n10.controller;

import com.example.demo.n10.model.entity.ExamType;
import com.example.demo.n10.service.ExamTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/exam-types")
@CrossOrigin(origins = "*")
public class ExamTypeController {

    private final ExamTypeService examTypeService;

    public ExamTypeController(ExamTypeService examTypeService) {
        this.examTypeService = examTypeService;
    }

    @GetMapping
    public List<ExamType> getAllExamTypes() {
        return examTypeService.findAll();
    }

    @GetMapping("/list")
    public List<ExamType> listAllExamTypes() {
        return examTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamType> getExamTypeById(@PathVariable UUID id) {
        return examTypeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ExamType createExamType(@RequestBody ExamType examType) {
        return examTypeService.save(examType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamType> updateExamType(@PathVariable UUID id, @RequestBody ExamType examType) {
        return examTypeService.findById(id)
                .map(existingExamType -> {
                    existingExamType.setName(examType.getName());
                    existingExamType.setDescription(examType.getDescription());
                    existingExamType.setIsActive(examType.getIsActive());
                    // Update other fields as necessary
                    return ResponseEntity.ok(examTypeService.save(existingExamType));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamType(@PathVariable UUID id) {
        if (examTypeService.findById(id).isPresent()) {
            examTypeService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
