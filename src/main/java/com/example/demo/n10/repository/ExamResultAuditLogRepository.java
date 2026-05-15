package com.example.demo.n10.repository;

import com.example.demo.n10.model.entity.ExamResultAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExamResultAuditLogRepository extends JpaRepository<ExamResultAuditLog, UUID> {
    List<ExamResultAuditLog> findByExamResultId(UUID examResultId);
    List<ExamResultAuditLog> findByChangedByAndChangedAtBetween(UUID changedBy, LocalDateTime start, LocalDateTime end);
    List<ExamResultAuditLog> findByExamResultIdOrderByChangedAtDesc(UUID examResultId);
}
