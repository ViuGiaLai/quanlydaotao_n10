package com.example.demo.n10.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.n10.model.entity.ExamRegistration;

@Repository
public interface ExamRegistrationRepository extends JpaRepository<ExamRegistration, UUID> {
    
    // Tìm theo examRoomId
    List<ExamRegistration> findByExamRoomId(String examRoomId);
    
    // Tìm theo examId
    List<ExamRegistration> findByExamId(String examId);
    
    // Tìm theo examId và examRoomId
    List<ExamRegistration> findByExamIdAndExamRoomId(String examId, String examRoomId);
    
    // Tìm theo studentId
    List<ExamRegistration> findByStudentId(String studentId);
    
    // Tìm theo isActive
    List<ExamRegistration> findByIsActive(Boolean isActive);
    
    // Query lấy tất cả đăng ký thi với thông tin exam, room, student
    @Query(value = """
        SELECT 
            reg.id, reg.exam_id, reg.exam_room_id, reg.student_id, reg.roll_number, reg.is_active,
            e.name as exam_name, r.exam_room_name, s.full_name as student_name, s.code as student_code
        FROM exam_registration reg
        LEFT JOIN exams e ON reg.exam_id = e.id
        LEFT JOIN exam_rooms r ON reg.exam_room_id = r.id
        LEFT JOIN students s ON reg.student_id = s.id
        WHERE reg.is_active = 1
        ORDER BY reg.created_at DESC
        """, nativeQuery = true)
    List<Object[]> findAllWithDetails();
}
