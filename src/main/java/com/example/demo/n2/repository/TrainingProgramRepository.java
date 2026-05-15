package com.example.demo.n2.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.n2.model.entity.TrainingProgram;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, UUID> {
    Optional<TrainingProgram> findByCode(String code);
    List<TrainingProgram> findByIsActiveTrue();
}