package com.example.demo.n10.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "exam_results")
public class ExamResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Version
    private Long version; // Optimistic locking to prevent concurrent modifications

    @Column(name = "registration_id", nullable = false)
    private UUID registrationId;

    @Column(name = "course_class_id")
    private UUID courseClassId;

    @Column(name = "subject_id")
    private UUID subjectId;

    @Column(name = "score", nullable = false)
    private Double score;

    // Các loại điểm thành phần
    @Min(value = 0, message = "Điểm chuyên cần không được âm")
    @Max(value = 10, message = "Điểm chuyên cần không được vượt quá 10")
    @Column(name = "attendance_score")
    private Double attendanceScore; // Điểm chuyên cần 10%

    @Min(value = 0, message = "Điểm kiểm tra không được âm")
    @Max(value = 10, message = "Điểm kiểm tra không được vượt quá 10")
    @Column(name = "test_score")
    private Double testScore; // Điểm kiểm tra 20%

    @Min(value = 0, message = "Điểm giữa kỳ không được âm")
    @Max(value = 10, message = "Điểm giữa kỳ không được vượt quá 10")
    @Column(name = "midterm_score")
    private Double midtermScore; // Điểm giữa kỳ 20%

    @Min(value = 0, message = "Điểm cuối kỳ không được âm")
    @Max(value = 10, message = "Điểm cuối kỳ không được vượt quá 10")
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

    // Trạng thái cho phép nhập điểm: OPEN / LOCKED
    @Column(name = "edit_status", length = 10)
    private String editStatus;

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

    // Trạng thái công bố: null = chưa công bố, PUBLISHED = đã công bố cho sinh viên
    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "published_by")
    private UUID publishedBy;

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

    public UUID getCourseClassId() {
        return courseClassId;
    }

    public void setCourseClassId(UUID courseClassId) {
        this.courseClassId = courseClassId;
    }

    public UUID getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(UUID subjectId) {
        this.subjectId = subjectId;
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
        
        // If no component scores entered, return null (not 0)
        if (attendanceScore == null && testScore == null && 
            midtermScore == null && finalScore == null) {
            return null;
        }
        
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

    // editStatus: OPEN = cho phép nhập, LOCKED = khóa nhập
    public String getEditStatus() { return editStatus; }
    public void setEditStatus(String editStatus) { this.editStatus = editStatus; }
    
    // Helper để tương thích ngược
    public Boolean getIsEditable() { 
        return "OPEN".equals(editStatus); 
    }
    public void setIsEditable(Boolean isEditable) { 
        this.editStatus = (isEditable != null && isEditable) ? "OPEN" : "LOCKED"; 
    }

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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public UUID getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(UUID publishedBy) {
        this.publishedBy = publishedBy;
    }
}
