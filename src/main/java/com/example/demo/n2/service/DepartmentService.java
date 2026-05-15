package com.example.demo.n2.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.n2.model.entity.Department;
import com.example.demo.n2.repository.DepartmentRepository;

@Service
public class DepartmentService {
    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public List<Department> findAll() {
        return repository.findAll();
    }

    public Optional<Department> findById(UUID id) {
        return repository.findById(id);
    }

    public List<Department> findActive() {
        return repository.findByIsActive(true);
    }

    public Department save(Department obj) {
        if (obj.getId() == null) {
            obj.setCreatedAt(LocalDateTime.now());
        }
        obj.setUpdatedAt(LocalDateTime.now());
        return repository.save(obj);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}