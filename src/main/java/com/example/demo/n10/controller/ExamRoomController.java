package com.example.demo.n10.controller;

import com.example.demo.n10.model.entity.ExamRoom;
import com.example.demo.n10.repository.ExamRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.n10.service.ExamRoomService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/exam-rooms")
@CrossOrigin("*")
public class ExamRoomController {

    @Autowired
    private ExamRoomService examRoomService; // Đổi từ Repository sang Service

    @GetMapping
    public List<ExamRoom> getAll() {
        return examRoomService.getAllExamRooms(); // Gọi qua Service
    }

    @GetMapping("/list")
    public List<ExamRoom> listAll() {
        return examRoomService.getAllExamRooms();
    }

    @PostMapping
    public ExamRoom save(@RequestBody ExamRoom examRoom) {
        return examRoomService.saveExamRoom(examRoom);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        examRoomService.deleteExamRoom(id);
    }

    // Thêm vào file ExamRoomController.java
    @PutMapping("/{id}")
    public ExamRoom update(@PathVariable UUID id, @RequestBody ExamRoom examRoom) {
        // Ép ID của đối tượng bằng với ID trên đường dẫn để đảm bảo cập nhật đúng dòng
        examRoom.setId(id);
        return examRoomService.saveExamRoom(examRoom);
    }
}