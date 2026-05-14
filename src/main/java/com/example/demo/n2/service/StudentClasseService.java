package com.example.demo.n2.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.example.demo.n2.model.entity.StudentClasse;
import com.example.demo.n2.repository.StudentClasseRepository;

@Service
public class StudentClasseService {
    private final StudentClasseRepository studentClasseRepository;

    public StudentClasseService(StudentClasseRepository studentClasseRepository) {
        this.studentClasseRepository = studentClasseRepository;
    }

    public List<StudentClasse> findAll() {
        return studentClasseRepository.findAll();
    }

    public Optional<StudentClasse> findById(UUID id) {
        return studentClasseRepository.findById(id);
    }

    public Optional<StudentClasse> findByCode(String code) {
        return studentClasseRepository.findByCode(code);
    }

    public List<StudentClasse> findByDepartmentId(UUID departmentId) {
        return studentClasseRepository.findByDepartmentId(departmentId);
    }

    public List<StudentClasse> findByMajorId(UUID majorId) {
        return studentClasseRepository.findByMajorId(majorId);
    }

    public List<StudentClasse> findByEmployeeId(UUID employeeId) {
        return studentClasseRepository.findByEmployeeId(employeeId);
    }

    public StudentClasse save(StudentClasse studentClasse) {
        return studentClasseRepository.save(studentClasse);
    }

    public void deleteById(UUID id) {
        studentClasseRepository.deleteById(id);
    }
}