package com.example.demo.n10.controller;

import com.example.demo.n10.model.entity.ExamRoom;
import com.example.demo.n10.repository.ExamRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.n10.service.ExamRoomService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/exam-rooms")
@CrossOrigin("*")
public class ExamRoomController {

    @Autowired
    private ExamRoomService examRoomService;

    // Lấy tất cả
    @GetMapping
    public List<ExamRoom> getAll() {
        return examRoomService.getAllExamRooms();
    }

    // Lấy tất cả (alias)
    @GetMapping("/list")
    public List<ExamRoom> listAll() {
        return examRoomService.getAllExamRooms();
    }

    // Chi tiết theo id
    @GetMapping("/{id}")
    public ExamRoom getById(@PathVariable UUID id) {
        return examRoomService.getExamRoomById(id)
                .orElseThrow(() -> new RuntimeException("ExamRoom not found: " + id));
    }

    // Lọc theo examId
    @GetMapping("/by-exam/{examId}")
    public List<ExamRoom> getByExamId(@PathVariable UUID examId) {
        return examRoomService.getExamRoomsByExamId(examId);
    }

    // Lọc theo roomId
    @GetMapping("/by-room/{roomId}")
    public List<ExamRoom> getByRoomId(@PathVariable UUID roomId) {
        return examRoomService.getExamRoomsByRoomId(roomId);
    }

    // Lọc đang hoạt động
    @GetMapping("/active")
    public List<ExamRoom> getActive() {
        return examRoomService.getActiveExamRooms();
    }

    // Tạo mới
    @PostMapping
    public ExamRoom save(@RequestBody ExamRoom examRoom) {
        return examRoomService.saveExamRoom(examRoom);
    }

    // Cập nhật
    @PutMapping("/{id}")
    public ExamRoom update(@PathVariable UUID id, @RequestBody ExamRoom examRoom) {
        examRoom.setId(id);
        return examRoomService.saveExamRoom(examRoom);
    }

    // Xóa cứng
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        examRoomService.deleteExamRoom(id);
    }

    // Soft delete
    @PatchMapping("/{id}/soft-delete")
    public void softDelete(@PathVariable UUID id) {
        examRoomService.softDeleteExamRoom(id);
    }
}