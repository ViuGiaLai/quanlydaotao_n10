package com.example.demo.n10.service;

import com.example.demo.n10.model.entity.CourseClass;
import com.example.demo.n10.repository.CourseClassRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseClassService {

    private final CourseClassRepository repository;

    public CourseClassService(CourseClassRepository repository) {
        this.repository = repository;
    }

    public List<CourseClass> findAll() {
        return repository.findAll();
    }

    public Optional<CourseClass> findById(UUID id) {
        return repository.findById(id);
    }

    public List<CourseClass> findBySemesterId(UUID semesterId) {
        return repository.findBySemesterId(semesterId);
    }

    public CourseClass save(CourseClass courseClass) {
        if (courseClass.getId() == null) {
            courseClass.setCreatedAt(LocalDateTime.now());
        }
        courseClass.setUpdatedAt(LocalDateTime.now());
        return repository.save(courseClass);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}