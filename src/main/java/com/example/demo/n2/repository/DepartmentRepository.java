package com.example.demo.n2.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.n2.model.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    Optional<Department> findByDepartmentCode(String departmentCode);
    List<Department> findByIsActive(Boolean isActive);
}