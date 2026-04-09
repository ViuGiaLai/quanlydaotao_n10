package com.example.demo.n10.repository;

import com.example.demo.n10.model.entity.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ExamTypeRepository extends JpaRepository<ExamType, UUID> {
}
