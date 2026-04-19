package com.example.demo.n10.service;

import com.example.demo.n10.model.entity.Exam;
import com.example.demo.n10.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Exam saveExam(Exam exam) {
        // Logic tự động tính thời gian kết thúc
        if (exam.getStartTime() != null && exam.getDurationMinutes() != null) {
            exam.setEndTime(exam.getStartTime().plusMinutes(exam.getDurationMinutes()));
        }
        return examRepository.save(exam);
    }

    // Trong file ExamService.java
    public void deleteExam(UUID id) {
        examRepository.deleteById(id);
    }
}