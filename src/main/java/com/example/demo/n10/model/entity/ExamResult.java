package com.example.demo.n10.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "exam_results")
public class ExamResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "registration_id", nullable = false)
    private UUID registrationId;

    @Column(name = "score", nullable = false)
    private Double score;

    // Các loại điểm thành phần
    @Column(name = "attendance_score")
    private Double attendanceScore; // Điểm chuyên cần 10%

    @Column(name = "test_score")
    private Double testScore; // Điểm kiểm tra 20%

    @Column(name = "midterm_score")
    private Double midtermScore; // Điểm giữa kỳ 20%

    @Column(name = "final_score")
    private Double finalScore; // Điểm cuối kỳ 50%

    @Column(name = "total_score")
    private Double totalScore; // Tổng điểm

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @Column(name = "graded_by", nullable = false)
    private LocalDateTime gradedBy;

    @Column(name = "graded_at", nullable = false)
    private LocalDateTime gradedAt;

    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked;

    // Admin cho phép Teacher nhập điểm không
    @Column(name = "is_editable")
    private Boolean isEditable;

    // Loại điểm: ATTENDANCE, TEST, MIDTERM, FINAL, TOTAL
    @Column(name = "score_type", length = 20)
    private String scoreType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    private UUID deletedBy;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(UUID registrationId) {
        this.registrationId = registrationId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    // Attendance Score
    public Double getAttendanceScore() { return attendanceScore; }
    public void setAttendanceScore(Double attendanceScore) { this.attendanceScore = attendanceScore; }

    // Test Score
    public Double getTestScore() { return testScore; }
    public void setTestScore(Double testScore) { this.testScore = testScore; }

    // Midterm Score
    public Double getMidtermScore() { return midtermScore; }
    public void setMidtermScore(Double midtermScore) { this.midtermScore = midtermScore; }

    // Final Score
    public Double getFinalScore() { return finalScore; }
    public void setFinalScore(Double finalScore) { this.finalScore = finalScore; }

    // Total Score - tính tự động
    public Double getTotalScore() { 
        if (totalScore != null) return totalScore;
        double total = 0;
        if (attendanceScore != null) total += attendanceScore * 0.1;
        if (testScore != null) total += testScore * 0.2;
        if (midtermScore != null) total += midtermScore * 0.2;
        if (finalScore != null) total += finalScore * 0.5;
        return Math.round(total * 100.0) / 100.0;
    }
    public void setTotalScore(Double totalScore) { this.totalScore = totalScore; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getGradedBy() {
        return gradedBy;
    }

    public void setGradedBy(LocalDateTime gradedBy) {
        this.gradedBy = gradedBy;
    }

    public LocalDateTime getGradedAt() {
        return gradedAt;
    }

    public void setGradedAt(LocalDateTime gradedAt) {
        this.gradedAt = gradedAt;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Boolean getIsEditable() { return isEditable; }
    public void setIsEditable(Boolean isEditable) { this.isEditable = isEditable; }

    public String getScoreType() { return scoreType; }
    public void setScoreType(String scoreType) { this.scoreType = scoreType; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public UUID getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UUID updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public UUID getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(UUID deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
