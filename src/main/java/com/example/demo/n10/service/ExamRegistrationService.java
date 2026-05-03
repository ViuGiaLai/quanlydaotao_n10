package com.example.demo.n10.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.n10.model.entity.ExamRegistration;
import com.example.demo.n10.repository.ExamRegistrationRepository;

@Service
public class ExamRegistrationService {
    
    private final ExamRegistrationRepository examRegistrationRepository;

    public ExamRegistrationService(ExamRegistrationRepository examRegistrationRepository) {
        this.examRegistrationRepository = examRegistrationRepository;
    }

    // Hiển thị tất cả đăng ký thi
    public List<ExamRegistration> findAll() {
        if (examRegistrationRepository.findAll().isEmpty()) {
            throw new RuntimeException("Không có đăng ký thi nào.");
        }
        return examRegistrationRepository.findAll();
    }

    // Tìm kiếm đăng ký thi theo ID
    public Optional<ExamRegistration> findById(UUID id) {
        if (!examRegistrationRepository.findById(id).isPresent()) {
            throw new RuntimeException("Đăng ký thi với ID " + id + " không tồn tại.");
        }
        return examRegistrationRepository.findById(id);
    }

    // Lưu hoặc cập nhật đăng ký thi
    public ExamRegistration save(ExamRegistration examRegistration) {
        if (examRegistration.getCreatedAt() == null) {
            examRegistration.setCreatedAt(LocalDateTime.now());
        }
        if (examRegistration.getIsActive() == null) {
            examRegistration.setIsActive(true);
        }
        examRegistration.setUpdatedAt(LocalDateTime.now());
        return examRegistrationRepository.save(examRegistration);
    }

    // Xóa đăng ký thi theo ID
    public void deleteById(UUID id) {
        if (!examRegistrationRepository.findById(id).isPresent()) {
            throw new RuntimeException("Đăng ký thi với ID " + id + " không tồn tại.");
        }
        examRegistrationRepository.deleteById(id);
    }
}
