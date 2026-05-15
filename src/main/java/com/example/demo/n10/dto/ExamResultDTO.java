package com.example.demo.n10.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ExamResultDTO {
    private UUID id;
    private UUID registrationId;
    private UUID courseClassId;
    private UUID subjectId;
    private Double score;
    private Double attendanceScore;
    private Double testScore;
    private Double midtermScore;
    private Double finalScore;
    private Double totalScore;
    private String status;
    private LocalDateTime gradedBy;
    private LocalDateTime gradedAt;
    private Boolean isLocked;
    private String editStatus;
    private String scoreType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private LocalDateTime publishedAt;
    
    // Thông tin student từ join
    private String studentCode;
    private String studentName;
    private String subjectName;
    private String className;
    
    // Constructor rỗng
    public ExamResultDTO() {}
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public UUID getRegistrationId() { return registrationId; }
    public void setRegistrationId(UUID registrationId) { this.registrationId = registrationId; }
    
    public UUID getCourseClassId() { return courseClassId; }
    public void setCourseClassId(UUID courseClassId) { this.courseClassId = courseClassId; }
    
    public UUID getSubjectId() { return subjectId; }
    public void setSubjectId(UUID subjectId) { this.subjectId = subjectId; }
    
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    
    public Double getAttendanceScore() { return attendanceScore; }
    public void setAttendanceScore(Double attendanceScore) { this.attendanceScore = attendanceScore; }
    
    public Double getTestScore() { return testScore; }
    public void setTestScore(Double testScore) { this.testScore = testScore; }
    
    public Double getMidtermScore() { return midtermScore; }
    public void setMidtermScore(Double midtermScore) { this.midtermScore = midtermScore; }
    
    public Double getFinalScore() { return finalScore; }
    public void setFinalScore(Double finalScore) { this.finalScore = finalScore; }
    
    public Double getTotalScore() { return totalScore; }
    public void setTotalScore(Double totalScore) { this.totalScore = totalScore; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getGradedBy() { return gradedBy; }
    public void setGradedBy(LocalDateTime gradedBy) { this.gradedBy = gradedBy; }
    
    public LocalDateTime getGradedAt() { return gradedAt; }
    public void setGradedAt(LocalDateTime gradedAt) { this.gradedAt = gradedAt; }
    
    public Boolean getIsLocked() { return isLocked; }
    public void setIsLocked(Boolean isLocked) { this.isLocked = isLocked; }
    
    public String getEditStatus() { return editStatus; }
    public void setEditStatus(String editStatus) { this.editStatus = editStatus; }
    
    public String getScoreType() { return scoreType; }
    public void setScoreType(String scoreType) { this.scoreType = scoreType; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
    
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    
    // Helper method cho isEditable (tương thích với code cũ)
    public Boolean getIsEditable() {
        return "OPEN".equals(editStatus);
    }
}