package com.example.demo.n10.service;

import com.example.demo.n10.model.entity.ExamType;
import com.example.demo.n10.repository.ExamTypeRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExamTypeService {

    private final ExamTypeRepository examTypeRepository;

    public ExamTypeService(ExamTypeRepository examTypeRepository) {
        this.examTypeRepository = examTypeRepository;
    }

    public List<ExamType> findAll() {
        return examTypeRepository.findAll();
    }

    public Optional<ExamType> findById(UUID id) {
        return examTypeRepository.findById(id);
    }

    public ExamType save(ExamType examType) {
        LocalDateTime now = LocalDateTime.now();
        if (examType.getId() == null) {
            // New entity - set createdAt
            examType.setCreatedAt(now);
        }
        if (examType.getIsActive() == null) {
            examType.setIsActive(true);
        }
        examType.setUpdatedAt(now);
        return examTypeRepository.save(examType);
    }

    public void deleteById(UUID id) {
        examTypeRepository.deleteById(id);
    }
}
