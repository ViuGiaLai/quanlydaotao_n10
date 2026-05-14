package com.example.demo.n2.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.n2.model.entity.Student;
import com.example.demo.n2.repository.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(UUID id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> findByUserId(UUID userId) {
        return studentRepository.findByUser_Id(userId);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    public void deleteById(UUID id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            student.setDeletedAt(LocalDateTime.now());
            student.setIsActive(false);
            studentRepository.save(student);
        }
    }

    public List<Student> search(String keyword) {
        return studentRepository.findAll();
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student update(UUID id, Student student) {
        student.setId(id);
        return studentRepository.save(student);
    }

    // Alias methods for API controller compatibility
    public List<Student> getAll() {
        return findAll();
    }

    public Student getById(UUID id) {
        return findById(id).orElse(null);
    }

    @Transactional
    public void delete(UUID id) {
        deleteById(id);
    }
}