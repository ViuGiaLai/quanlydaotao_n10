package com.example.demo.n10.model.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "course_class_subjects")
public class CourseClassSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "course_class_id", nullable = false)
    private UUID courseClassId;

    @Column(name = "subject_id", nullable = false)
    private UUID subjectId;

    @Column(name = "teacher_id")
    private UUID teacherId;

    // Getters & Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getCourseClassId() { return courseClassId; }
    public void setCourseClassId(UUID courseClassId) { this.courseClassId = courseClassId; }

    public UUID getSubjectId() { return subjectId; }
    public void setSubjectId(UUID subjectId) { this.subjectId = subjectId; }

    public UUID getTeacherId() { return teacherId; }
    public void setTeacherId(UUID teacherId) { this.teacherId = teacherId; }
}