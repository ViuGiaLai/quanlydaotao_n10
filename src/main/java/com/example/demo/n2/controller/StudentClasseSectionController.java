package com.example.demo.n2.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.n2.model.entity.StudentClasseSection;
import com.example.demo.n2.service.StudentClasseSectionService;

@RestController
@RequestMapping("/api/student-classe-sections")
@CrossOrigin(origins = "*")
public class StudentClasseSectionController {

    private final StudentClasseSectionService service;

    public StudentClasseSectionController(StudentClasseSectionService service) {
        this.service = service;
    }

    @GetMapping
    public List<StudentClasseSection> getAll(
            @RequestParam(required = false) UUID studentId,
            @RequestParam(required = false) UUID studentClasseId) {
        if (studentId != null) {
            return service.findByStudentId(studentId);
        }
        if (studentClasseId != null) {
            return service.findByStudentClasseId(studentClasseId);
        }
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentClasseSection> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public StudentClasseSection create(@RequestBody StudentClasseSection obj) {
        return service.save(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentClasseSection> update(@PathVariable UUID id, @RequestBody StudentClasseSection obj) {
        return service.findById(id)
                .map(existing -> {
                    existing.setStudentId(obj.getStudentId());
                    existing.setStudentClasseId(obj.getStudentClasseId());
                    existing.setStatus(obj.getStatus());
                    existing.setNote(obj.getNote());
                    existing.setStartDate(obj.getStartDate());
                    existing.setEndDate(obj.getEndDate());
                    existing.setIsActive(obj.getIsActive());
                    return ResponseEntity.ok(service.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}