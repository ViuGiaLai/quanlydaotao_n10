package com.example.demo.n10.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Audit log for exam result changes.
 * Tracks: who changed what, when, from what value to what value.
 * Required for dispute resolution and compliance.
 */
@Entity
@Table(name = "exam_result_audit_logs", indexes = {
    @Index(name = "idx_exam_result_id", columnList = "exam_result_id"),
    @Index(name = "idx_changed_by", columnList = "changed_by"),
    @Index(name = "idx_changed_at", columnList = "changed_at")
})
public class ExamResultAuditLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "exam_result_id", nullable = false)
    private UUID examResultId;

    @Column(name = "changed_by", nullable = false)
    private UUID changedBy; // User who made the change

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    @Column(name = "field_name", length = 50, nullable = false)
    private String fieldName; // e.g., "attendance_score", "status", "edit_status"

    @Column(name = "old_value", length = 255)
    private String oldValue;

    @Column(name = "new_value", length = 255)
    private String newValue;

    @Column(name = "change_reason", length = 255)
    private String changeReason; // Optional: why was this changed?

    @Column(name = "ip_address", length = 45)
    private String ipAddress; // For security tracking

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getExamResultId() { return examResultId; }
    public void setExamResultId(UUID examResultId) { this.examResultId = examResultId; }

    public UUID getChangedBy() { return changedBy; }
    public void setChangedBy(UUID changedBy) { this.changedBy = changedBy; }

    public LocalDateTime getChangedAt() { return changedAt; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }

    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }

    public String getOldValue() { return oldValue; }
    public void setOldValue(String oldValue) { this.oldValue = oldValue; }

    public String getNewValue() { return newValue; }
    public void setNewValue(String newValue) { this.newValue = newValue; }

    public String getChangeReason() { return changeReason; }
    public void setChangeReason(String changeReason) { this.changeReason = changeReason; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
}
