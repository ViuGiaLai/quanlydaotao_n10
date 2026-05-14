package com.example.demo.n10.controller;

import com.example.demo.n10.model.entity.Subject;
import com.example.demo.n10.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "*")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.findAll();
    }

    @GetMapping("/list")
    public List<Subject> listAllSubjects() {
        return subjectService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable UUID id) {
        return subjectService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Subject createSubject(@RequestBody Subject subject) {
        return subjectService.save(subject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable UUID id, @RequestBody Subject subject) {
        return subjectService.findById(id)
                .map(existingSubject -> {
                    existingSubject.setSubjectCode(subject.getSubjectCode());
                    existingSubject.setSubjectName(subject.getSubjectName());
                    existingSubject.setDescription(subject.getDescription());
                    existingSubject.setCredits(subject.getCredits());
                    existingSubject.setDepartment(subject.getDepartment());
                    existingSubject.setIsActive(subject.getIsActive());
                    return ResponseEntity.ok(subjectService.save(existingSubject));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable UUID id) {
        if (subjectService.findById(id).isPresent()) {
            subjectService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}