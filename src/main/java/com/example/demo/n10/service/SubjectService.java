package com.example.demo.n10.service;

import com.example.demo.n10.model.entity.Subject;
import com.example.demo.n10.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubjectService {

    private final SubjectRepository repository;

    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

    public List<Subject> findAll() {
        return repository.findAll();
    }

    public Optional<Subject> findById(UUID id) {
        return repository.findById(id);
    }

    public Subject save(Subject subject) {
        if (subject.getId() == null) {
            subject.setCreatedAt(LocalDateTime.now());
        }
        subject.setUpdatedAt(LocalDateTime.now());
        return repository.save(subject);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}