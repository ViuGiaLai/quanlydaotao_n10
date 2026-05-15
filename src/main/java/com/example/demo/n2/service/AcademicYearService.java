package com.example.demo.n2.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.n2.model.entity.AcademicYear;
import com.example.demo.n2.repository.AcademicYearRepository;

@Service
public class AcademicYearService {
    private final AcademicYearRepository repository;

    public AcademicYearService(AcademicYearRepository repository) {
        this.repository = repository;
    }

    public List<AcademicYear> findAll() {
        return repository.findAll();
    }

    public Optional<AcademicYear> findById(UUID id) {
        return repository.findById(id);
    }

    public List<AcademicYear> findActive() {
        return repository.findByIsActiveTrue();
    }

    public AcademicYear save(AcademicYear obj) {
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