package com.example.demo.n10.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.n10.model.entity.ExamResult;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, UUID> {
    
}
