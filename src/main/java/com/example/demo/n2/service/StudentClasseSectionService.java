package com.example.demo.n2.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.example.demo.n2.model.entity.StudentClasseSection;
import com.example.demo.n2.repository.StudentClasseSectionRepository;

@Service
public class StudentClasseSectionService {
    private final StudentClasseSectionRepository studentClasseSectionRepository;

    public StudentClasseSectionService(StudentClasseSectionRepository studentClasseSectionRepository) {
        this.studentClasseSectionRepository = studentClasseSectionRepository;
    }

    public List<StudentClasseSection> findAll() {
        return studentClasseSectionRepository.findAll();
    }

    public Optional<StudentClasseSection> findById(UUID id) {
        return studentClasseSectionRepository.findById(id);
    }

    public List<StudentClasseSection> findByStudentId(UUID studentId) {
        return studentClasseSectionRepository.findByStudentId(studentId);
    }

    public List<StudentClasseSection> findByStudentClasseId(UUID studentClasseId) {
        return studentClasseSectionRepository.findByStudentClasseId(studentClasseId);
    }

    public Optional<StudentClasseSection> findByStudentIdAndStatus(UUID studentId, String status) {
        return studentClasseSectionRepository.findByStudentIdAndStatus(studentId, status);
    }

    public StudentClasseSection save(StudentClasseSection studentClasseSection) {
        return studentClasseSectionRepository.save(studentClasseSection);
    }

    public void deleteById(UUID id) {
        studentClasseSectionRepository.deleteById(id);
    }

    public List<StudentClasseSection> importBatch(List<StudentClasseSection> items) {
        return items.stream()
                .map(item -> {
                    item.setCreatedAt(java.time.LocalDateTime.now());
                    item.setIsActive(true);
                    return studentClasseSectionRepository.save(item);
                })
                .toList();
    }
}