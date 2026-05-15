package com.example.demo.n10.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.example.demo.n10.dto.ToggleEditRequest;
import com.example.demo.n10.dto.ToggleEditMultipleRequest;
import com.example.demo.n10.dto.AdminEditRequest;
import com.example.demo.n10.dto.ExamResultDTO;
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
    public List<ExamResultDTO> getExamResults(
            @RequestParam(required = false) UUID courseClassId,
            @RequestParam(required = false) UUID subjectId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Boolean isEditable,
            @RequestParam(required = false) String search) {
        // Nếu có courseClassId, dùng query có join
        if (courseClassId != null) {
            return examResultService.findByCourseClassIdWithStudent(courseClassId);
        }
        // Mặc định lấy tất cả với student info
        return examResultService.findAllWithStudent();
    }

    @GetMapping("/list")
    public List<ExamResultDTO> listAllExamResults() {
        return examResultService.findAllWithStudent();
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

    // Toggle editable - Admin mở/khóa nhập điểm cho 1 record
    @PostMapping("/{id}/toggle-edit")
    public ResponseEntity<ExamResult> toggleEditable(@PathVariable UUID id, @RequestBody ToggleEditRequest request) {
        if (request.isEditable() == null) {
            return ResponseEntity.badRequest().build();
        }
        return examResultService.findById(id)
                .map(existing -> {
                    existing.setIsEditable(request.isEditable());
                    return ResponseEntity.ok(examResultService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Toggle editable hàng loạt - Admin mở/khóa nhập điểm cho nhiều sinh viên
    @PostMapping("/toggle-edit-multiple")
    public ResponseEntity<List<ExamResult>> toggleEditableMultiple(@RequestBody ToggleEditMultipleRequest request) {
        if (request.isEditable() == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            List<ExamResult> results = new java.util.ArrayList<>();
            for (UUID id : request.ids()) {
                var result = examResultService.findById(id);
                result.ifPresent(r -> {
                    r.setIsEditable(request.isEditable());
                    results.add(examResultService.save(r));
                });
            }
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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

    // ========== Score Status Endpoints ==========
    
    /**
     * Gửi điểm (DRAFT → SUBMITTED)
     * Teacher bấm "Gửi điểm"
     */
    @PostMapping("/{id}/submit")
    public ResponseEntity<ExamResult> submitScore(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(examResultService.submitScore(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Gửi nhiều điểm cùng lúc
     */
    @PostMapping("/submit-multiple")
    public ResponseEntity<List<ExamResult>> submitScores(@RequestBody List<UUID> resultIds) {
        try {
            return ResponseEntity.ok(examResultService.submitScores(resultIds));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Duyệt điểm (SUBMITTED → APPROVED)
     * Admin bấm "Duyệt"
     */
    @PostMapping("/{id}/approve")
    public ResponseEntity<ExamResult> approveScore(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(examResultService.approveScore(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Duyệt nhiều điểm cùng lúc
     */
    @PostMapping("/approve-multiple")
    public ResponseEntity<List<ExamResult>> approveScores(@RequestBody List<UUID> resultIds) {
        try {
            return ResponseEntity.ok(examResultService.approveScores(resultIds));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Trả lại điểm (SUBMITTED → DRAFT)
     * Admin bấm "Trả lại"
     */
    @PostMapping("/{id}/return")
    public ResponseEntity<ExamResult> returnScore(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(examResultService.returnScore(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Trả lại nhiều điểm cùng lúc
     */
    @PostMapping("/return-multiple")
    public ResponseEntity<List<ExamResult>> returnScores(@RequestBody List<UUID> resultIds) {
        try {
            return ResponseEntity.ok(examResultService.returnScores(resultIds));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Khóa điểm (APPROVED → LOCKED)
     * Admin bấm "Khóa"
     */
    @PostMapping("/{id}/lock")
    public ResponseEntity<ExamResult> lockScore(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(examResultService.lockScore(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Kiểm tra sinh viên có thể xem điểm không
     */
    @GetMapping("/{id}/can-view")
    public ResponseEntity<Boolean> canStudentView(@PathVariable UUID id) {
        return ResponseEntity.ok(examResultService.canStudentView(id));
    }

    // Export
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportResults(
            @RequestParam(required = false) UUID courseClassId,
            @RequestParam(required = false) UUID subjectId,
            @RequestParam(defaultValue = "excel") String format) {
        List<ExamResult> results = examResultService.findByFilters(courseClassId, subjectId, null, null, null);
        // TODO: Implement export to Excel/PDF
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=diem." + format)
                .body(new byte[0]);
    }

}
