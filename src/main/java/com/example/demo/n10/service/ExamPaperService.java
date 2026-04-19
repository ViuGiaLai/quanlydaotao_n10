package com.example.demo.n10.service;

import com.example.demo.n10.model.entity.ExamPaper;
import com.example.demo.n10.repository.ExamPaperRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExamPaperService {

    private final ExamPaperRepository examPaperRepository;

    public ExamPaperService(ExamPaperRepository examPaperRepository) {
        this.examPaperRepository = examPaperRepository;
    }

    public List<ExamPaper> findAll() {
        return examPaperRepository.findAll();
    }

    public Optional<ExamPaper> findById(UUID id) {
        return examPaperRepository.findById(id);
    }

    public ExamPaper save(ExamPaper examPaper) {
        if (examPaper.getCreatedAt() == null) {
            examPaper.setCreatedAt(LocalDateTime.now());
        }
        if (examPaper.getIsActive() == null) {
            examPaper.setIsActive(true);
        }
        examPaper.setUpdatedAt(LocalDateTime.now());
        return examPaperRepository.save(examPaper);
    }

    public void deleteById(UUID id) {
        examPaperRepository.deleteById(id);
    }
}
