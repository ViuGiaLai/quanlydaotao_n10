package com.example.demo.n2.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.demo.n2.model.entity.Student;
import com.example.demo.n2.repository.StudentRepository;

import java.util.UUID;

@Service
public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> getAll() {
        return repo.findAll();
    }

    public Student getById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public Student create(Student student) {
        return repo.save(student);   // UUID tự sinh ở đây
    }

    public Student update(UUID id, Student student) {
        Student old = getById(id);
        if (old == null) return null;

        old.setFullname(student.getFullname());

        return repo.save(old);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }

    public List<Student> search(String fullname) {
        return repo.findByFullnameContainingIgnoreCase(fullname);
    }
}