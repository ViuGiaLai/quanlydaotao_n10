package com.example.demo.n2.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.n2.model.entity.TrainingProgram;
import com.example.demo.n2.repository.TrainingProgramRepository;

@Service
public class TrainingProgramService {
    private final TrainingProgramRepository repository;

    public TrainingProgramService(TrainingProgramRepository repository) {
        this.repository = repository;
    }

    public List<TrainingProgram> findAll() {
        return repository.findAll();
    }

    public Optional<TrainingProgram> findById(UUID id) {
        return repository.findById(id);
    }

    public List<TrainingProgram> findActive() {
        return repository.findByIsActive(true);
    }

    public TrainingProgram save(TrainingProgram obj) {
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