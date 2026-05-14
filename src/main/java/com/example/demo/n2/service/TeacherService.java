package com.example.demo.n2.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.example.demo.n2.model.entity.Teacher;
import com.example.demo.n2.repository.TeacherRepository;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> findById(UUID id) {
        return teacherRepository.findById(id);
    }

    public Optional<Teacher> findByUserId(UUID userId) {
        return teacherRepository.findByUser_Id(userId);
    }
    
    public Optional<Teacher> findByCode(String code) {
        return teacherRepository.findByCode(code);
    }

    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher create(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher update(UUID id, Teacher teacher) {
        return findById(id).map(existing -> {
            existing.setCode(teacher.getCode());
            existing.setFullname(teacher.getFullname());
            existing.setDateOfBirth(teacher.getDateOfBirth());
            existing.setGender(teacher.getGender());
            existing.setEmail(teacher.getEmail());
            existing.setPhone(teacher.getPhone());
            existing.setDepartmentId(teacher.getDepartmentId());
            existing.setAcademicTitle(teacher.getAcademicTitle());
            existing.setDegree(teacher.getDegree());
            existing.setSpecialization(teacher.getSpecialization());
            existing.setStatus(teacher.getStatus());
            existing.setIsActive(teacher.getIsActive());
            return teacherRepository.save(existing);
        }).orElse(null);
    }

    public void deleteById(UUID id) {
        teacherRepository.deleteById(id);
    }

    // Alias methods for API controller compatibility
    public List<Teacher> getAll() {
        return findAll();
    }

    public Teacher getById(UUID id) {
        return findById(id).orElse(null);
    }

    public void delete(UUID id) {
        deleteById(id);
    }
}