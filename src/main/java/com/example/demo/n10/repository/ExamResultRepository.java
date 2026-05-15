package com.example.demo.n10.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.n10.model.entity.ExamResult;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, UUID> {
    
    // Query lấy tất cả exam_results với thông tin student
    // Join với exam_registration (không có s) và students
    @Query(value = """
        SELECT 
            er.id, er.registration_id, er.course_class_id, er.subject_id, er.score,
            er.attendance_score, er.test_score, er.midterm_score, er.final_score, er.total_score,
            er.status, er.graded_by, er.graded_at, er.is_locked, er.edit_status, er.score_type,
            er.created_at, er.updated_at, er.created_by, er.updated_by, er.deleted_at, er.deleted_by,
            er.is_active, er.published_at, er.published_by,
            s.code as student_code, s.full_name as student_name,
            sub.subject_name, cc.class_name
        FROM exam_results er
        LEFT JOIN exam_registration reg ON er.registration_id = reg.id
        LEFT JOIN students s ON reg.student_id = s.id
        LEFT JOIN subjects sub ON er.subject_id = sub.id
        LEFT JOIN course_classes cc ON er.course_class_id = cc.id
        WHERE er.is_active = 1
        ORDER BY er.created_at DESC
        """, nativeQuery = true)
    List<Object[]> findAllWithStudent();
    
    // Query lọc theo courseClassId
    @Query(value = """
        SELECT 
            er.id, er.registration_id, er.course_class_id, er.subject_id, er.score,
            er.attendance_score, er.test_score, er.midterm_score, er.final_score, er.total_score,
            er.status, er.graded_by, er.graded_at, er.is_locked, er.edit_status, er.score_type,
            er.created_at, er.updated_at, er.created_by, er.updated_by, er.deleted_at, er.deleted_by,
            er.is_active, er.published_at, er.published_by,
            s.code as student_code, s.full_name as student_name,
            sub.subject_name, cc.class_name
        FROM exam_results er
        LEFT JOIN exam_registration reg ON er.registration_id = reg.id
        LEFT JOIN students s ON reg.student_id = s.id
        LEFT JOIN subjects sub ON er.subject_id = sub.id
        LEFT JOIN course_classes cc ON er.course_class_id = cc.id
        WHERE er.is_active = 1 AND er.course_class_id = :courseClassId
        ORDER BY er.created_at DESC
        """, nativeQuery = true)
    List<Object[]> findByCourseClassIdWithStudent(@Param("courseClassId") UUID courseClassId);
}
