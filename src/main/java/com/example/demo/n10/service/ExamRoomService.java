package com.example.demo.n10.service;

import com.example.demo.n10.model.entity.ExamRoom;
import com.example.demo.n10.repository.ExamRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExamRoomService {

    @Autowired
    private ExamRoomRepository examRoomRepository;

    // Lấy tất cả
    public List<ExamRoom> getAllExamRooms() {
        return examRoomRepository.findAll();
    }

    // Lưu/Cập nhật
    public ExamRoom saveExamRoom(ExamRoom examRoom) {
        return examRoomRepository.save(examRoom);
    }

    // Xóa cứng
    public void deleteExamRoom(UUID id) {
        examRoomRepository.deleteById(id);
    }

    // Lấy theo examId
    public List<ExamRoom> getExamRoomsByExamId(UUID examId) {
        return examRoomRepository.findByExamId(examId);
    }

    // Lấy theo roomId
    public List<ExamRoom> getExamRoomsByRoomId(UUID roomId) {
        return examRoomRepository.findByRoomId(roomId);
    }

    // Lấy theo trạng thái hoạt động
    public List<ExamRoom> getActiveExamRooms() {
        return examRoomRepository.findByIsActiveTrue();
    }

    // Lấy chi tiết theo id
    public Optional<ExamRoom> getExamRoomById(UUID id) {
        return examRoomRepository.findById(id);
    }

    // Lấy tên phòng thi
    public String getExamRoomName(UUID examRoomId) {
        if (examRoomId == null) return "-";
        return examRoomRepository.findById(examRoomId)
                .map(ExamRoom::getExamRoomName)
                .orElse("-");
    }

    // Soft delete - cập nhật isActive = false
    public void softDeleteExamRoom(UUID id) {
        Optional<ExamRoom> opt = examRoomRepository.findById(id);
        if (opt.isPresent()) {
            ExamRoom examRoom = opt.get();
            examRoom.setIsActive(false);
            examRoom.setDeletedAt(LocalDateTime.now());
            examRoomRepository.save(examRoom);
        }
    }
}