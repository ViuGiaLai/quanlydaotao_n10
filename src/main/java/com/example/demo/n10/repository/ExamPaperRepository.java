package com.example.demo.n10.repository;

import com.example.demo.n10.model.entity.ExamPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ExamPaperRepository extends JpaRepository<ExamPaper, UUID> {
}
