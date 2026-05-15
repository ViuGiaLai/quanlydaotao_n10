package com.example.demo.n2.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.n2.model.entity.AcademicYear;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, UUID> {
    Optional<AcademicYear> findByYearCode(String yearCode);
    List<AcademicYear> findByIsActive(Boolean isActive);
}