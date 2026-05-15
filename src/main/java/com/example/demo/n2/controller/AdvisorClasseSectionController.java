package com.example.demo.n2.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.n2.model.entity.AdvisorClasseSection;
import com.example.demo.n2.service.AdvisorClasseSectionService;

@RestController
@RequestMapping("/api/advisor-classe-sections")
@CrossOrigin(origins = "*")
public class AdvisorClasseSectionController {

    private final AdvisorClasseSectionService service;

    public AdvisorClasseSectionController(AdvisorClasseSectionService service) {
        this.service = service;
    }

    @GetMapping
    public List<AdvisorClasseSection> getAll(
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false) UUID studentClasseId) {
        if (employeeId != null) {
            return service.findByEmployeeId(employeeId);
        }
        if (studentClasseId != null) {
            return service.findByStudentClasseId(studentClasseId);
        }
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvisorClasseSection> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AdvisorClasseSection create(@RequestBody AdvisorClasseSection obj) {
        return service.save(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvisorClasseSection> update(@PathVariable UUID id, @RequestBody AdvisorClasseSection obj) {
        return service.findById(id)
                .map(existing -> {
                    existing.setEmployeeId(obj.getEmployeeId());
                    existing.setStudentClasseId(obj.getStudentClasseId());
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