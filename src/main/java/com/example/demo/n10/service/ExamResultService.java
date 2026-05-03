package com.example.demo.n10.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.n10.model.entity.ExamResult;
import com.example.demo.n10.repository.ExamResultRepository;

@Service
public class ExamResultService {

    private final ExamResultRepository examResultRepository;

    public ExamResultService(ExamResultRepository examResultRepository) {
        this.examResultRepository = examResultRepository;
    }

    // Tìm kiếm tất cả kết quả thi
    public List<ExamResult> findAll() {
        if (examResultRepository.findAll().isEmpty()) {
            throw new RuntimeException("Không có kết quả thi nào.");
        }
        return examResultRepository.findAll();
    }

    // Tìm kiếm kết quả thi theo ID
    public Optional<ExamResult> findById(UUID id) {
        if (!examResultRepository.findById(id).isPresent()) {
            throw new RuntimeException("Kết quả thi với ID " + id + " không tồn tại.");
        } 
        return examResultRepository.findById(id);
    }

    // Lưu hoặc cập nhật kết quả thi
    public ExamResult save(ExamResult examResult) {
        if (examResult.getCreatedAt() == null) {
            examResult.setCreatedAt(LocalDateTime.now());
        }
        if (examResult.getIsActive() == null) {
            examResult.setIsActive(true);
        }
        examResult.setUpdatedAt(LocalDateTime.now());
        return examResultRepository.save(examResult);
    }

    // Xóa kết quả thi theo ID
    public void deleteById(UUID id) {
        if (!examResultRepository.findById(id).isPresent()) {
            throw new RuntimeException("Kết quả thi với ID " + id + " không tồn tại.");
        }
        examResultRepository.deleteById(id);
    }
}
