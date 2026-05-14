package com.example.demo.n10.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.example.demo.n10.model.entity.ExamResult;
import com.example.demo.n10.service.ExamResultService;

@RestController
@RequestMapping("/api/exam-results")
@CrossOrigin("*")
public class ExamResultController {
    
    private final ExamResultService examResultService;
    
    public ExamResultController(ExamResultService examResultService) {
        this.examResultService = examResultService;
    }

    @GetMapping
    public List<ExamResult> getExamResults(
            @RequestParam(required = false) UUID courseClassId,
            @RequestParam(required = false) UUID subjectId,
            @RequestParam(required = false) Boolean isEditable,
            @RequestParam(required = false) String search) {
        return examResultService.findByFilters(courseClassId, subjectId, isEditable, search);
    }

    @GetMapping("/list")
    public List<ExamResult> listAllExamResults() {
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

    // Toggle editable - Admin mở/khóa nhập điểm
    @PostMapping("/{id}/toggle-edit")
    public ResponseEntity<ExamResult> toggleEditable(@PathVariable UUID id, @RequestBody ToggleEditRequest request) {
        return examResultService.findById(id)
                .map(existing -> {
                    existing.setIsEditable(request.isEditable());
                    return ResponseEntity.ok(examResultService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Admin special edit - chỉnh sửa đặc biệt
    @PostMapping("/{id}/admin-edit")
    public ResponseEntity<ExamResult> adminEdit(@PathVariable UUID id, @RequestBody AdminEditRequest request) {
        return examResultService.findById(id)
                .map(existing -> {
                    if (request.attendanceScore() != null) existing.setAttendanceScore(request.attendanceScore());
                    if (request.testScore() != null) existing.setTestScore(request.testScore());
                    if (request.midtermScore() != null) existing.setMidtermScore(request.midtermScore());
                    if (request.finalScore() != null) existing.setFinalScore(request.finalScore());
                    return ResponseEntity.ok(examResultService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Export
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportResults(
            @RequestParam(required = false) UUID courseClassId,
            @RequestParam(required = false) UUID subjectId,
            @RequestParam(defaultValue = "excel") String format) {
        List<ExamResult> results = examResultService.findByFilters(courseClassId, subjectId, null, null);
        // TODO: Implement export to Excel/PDF
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=diem." + format)
                .body(new byte[0]);
    }

    // Request records
    public record ToggleEditRequest(Boolean isEditable) {}
    public record AdminEditRequest(
            Double attendanceScore,
            Double testScore,
            Double midtermScore,
            Double finalScore,
            String adminNote) {}

}
