package com.example.demo.n10.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
