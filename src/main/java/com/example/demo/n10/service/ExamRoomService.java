package com.example.demo.n10.service;

import com.example.demo.n10.model.entity.ExamRoom;
import com.example.demo.n10.repository.ExamRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service // Đánh dấu đây là tầng Service
public class ExamRoomService {

    @Autowired
    private ExamRoomRepository examRoomRepository;

    public List<ExamRoom> getAllExamRooms() {
        return examRoomRepository.findAll();
    }

    public ExamRoom saveExamRoom(ExamRoom examRoom) {
        return examRoomRepository.save(examRoom);
    }

    public void deleteExamRoom(UUID id) {
        examRoomRepository.deleteById(id);
    }
}