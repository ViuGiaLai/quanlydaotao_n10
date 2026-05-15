package com.example.demo.n2.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.n2.model.entity.Major;
import com.example.demo.n2.repository.MajorRepository;

@Service
public class MajorService {
    private final MajorRepository repository;

    public MajorService(MajorRepository repository) {
        this.repository = repository;
    }

    public List<Major> findAll() {
        return repository.findAll();
    }

    public Optional<Major> findById(UUID id) {
        return repository.findById(id);
    }

    public List<Major> findByDepartmentId(UUID departmentId) {
        return repository.findByDepartmentId(departmentId);
    }

    public List<Major> findActive() {
        return repository.findByIsActive(true);
    }

    public Major save(Major obj) {
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