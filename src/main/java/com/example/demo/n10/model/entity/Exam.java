package com.example.demo.n10.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "exams")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; // STT 1

    @Column(name = "exam_type_id", nullable = false)
    private UUID examTypeId; // STT 2

    @Column(name = "course_class_id", nullable = false)
    private UUID courseClassId; // STT 3

    @Column(name = "semester_id", nullable = false)
    private UUID semesterId; // STT 4

    @Column(name = "exam_date", nullable = false)
    private LocalDate examDate; // STT 5

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime; // STT 6

    @Column(name = "duration_minutes")
    private Integer durationMinutes; // STT 7

    @Column(name = "end_time")
    private LocalTime endTime; // STT 8

    @Column(name = "exam_format", length = 20)
    private String examFormat; // STT 9

    @Column(name = "exam_status", length = 20)
    private String examStatus; // STT 10

    @Column(name = "supervisor_count")
    private Integer supervisorCount; // STT 11

    // --- Các trường hệ thống bổ sung để khớp với ảnh thiết kế ---
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // STT 12

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // STT 13

    @Column(name = "created_by")
    private UUID createdBy; // STT 14

    @Column(name = "updated_by")
    private UUID updatedBy; // STT 15

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // STT 16

    @Column(name = "deleted_by")
    private UUID deletedBy; // STT 17

    @Column(name = "is_active")
    private Boolean isActive = true; // STT 18
}