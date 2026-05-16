package com.example.demo.n10.service;

import com.example.demo.n10.model.entity.Semester;
import com.example.demo.n10.repository.SemesterRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SemesterService {

    private final SemesterRepository repository;

    public SemesterService(SemesterRepository repository) {
        this.repository = repository;
    }

    public List<Semester> findAll() {
        return repository.findAll();
    }

    public List<Semester> findActive() {
        return repository.findByIsActiveTrue();
    }

    public Optional<Semester> findById(UUID id) {
        return repository.findById(id);
    }

    public Semester save(Semester semester) {
        if (semester.getId() == null) {
            semester.setCreatedAt(LocalDateTime.now());
            if (semester.getIsActive() == null) {
                semester.setIsActive(true);
            }
        }
        semester.setUpdatedAt(LocalDateTime.now());
        return repository.save(semester);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}