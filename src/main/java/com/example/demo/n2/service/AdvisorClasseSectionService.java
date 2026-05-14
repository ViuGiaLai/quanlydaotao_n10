package com.example.demo.n2.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.example.demo.n2.model.entity.AdvisorClasseSection;
import com.example.demo.n2.repository.AdvisorClasseSectionRepository;

@Service
public class AdvisorClasseSectionService {
    private final AdvisorClasseSectionRepository advisorClasseSectionRepository;

    public AdvisorClasseSectionService(AdvisorClasseSectionRepository advisorClasseSectionRepository) {
        this.advisorClasseSectionRepository = advisorClasseSectionRepository;
    }

    public List<AdvisorClasseSection> findAll() {
        return advisorClasseSectionRepository.findAll();
    }

    public Optional<AdvisorClasseSection> findById(UUID id) {
        return advisorClasseSectionRepository.findById(id);
    }

    public List<AdvisorClasseSection> findByEmployeeId(UUID employeeId) {
        return advisorClasseSectionRepository.findByEmployeeId(employeeId);
    }

    public List<AdvisorClasseSection> findByStudentClasseId(UUID studentClasseId) {
        return advisorClasseSectionRepository.findByStudentClasseId(studentClasseId);
    }

    public AdvisorClasseSection save(AdvisorClasseSection advisorClasseSection) {
        return advisorClasseSectionRepository.save(advisorClasseSection);
    }

    public void deleteById(UUID id) {
        advisorClasseSectionRepository.deleteById(id);
    }
}