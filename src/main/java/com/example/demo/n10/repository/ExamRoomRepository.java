package com.example.demo.n10.repository;

import com.example.demo.n10.model.entity.ExamRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExamRoomRepository extends JpaRepository<ExamRoom, UUID> {
    
    // Tìm theo examId
    List<ExamRoom> findByExamId(UUID examId);
    
    // Tìm theo roomId
    List<ExamRoom> findByRoomId(UUID roomId);
    
    // Tìm theo trạng thái hoạt động
    List<ExamRoom> findByIsActiveTrue();
    
    // Tìm theo examId và isActive
    List<ExamRoom> findByExamIdAndIsActiveTrue(UUID examId);
    
    // Tìm chi tiết theo id
    Optional<ExamRoom> findById(UUID id);
}