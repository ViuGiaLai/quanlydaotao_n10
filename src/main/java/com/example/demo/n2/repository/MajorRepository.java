package com.example.demo.n2.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.n2.model.entity.Major;

@Repository
public interface MajorRepository extends JpaRepository<Major, UUID> {
    Optional<Major> findByMajorCode(String majorCode);
    List<Major> findByDepartmentId(UUID departmentId);
    List<Major> findByIsActive(Boolean isActive);
}