package com.example.demo.n2.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.n2.model.entity.AcademicYear;
import com.example.demo.n2.service.AcademicYearService;

@RestController
@RequestMapping("/api/academic-years")
@CrossOrigin(origins = "*")
public class AcademicYearController {

    private final AcademicYearService service;

    public AcademicYearController(AcademicYearService service) {
        this.service = service;
    }

    @GetMapping
    public List<AcademicYear> getAll() {
        return service.findAll();
    }

    @GetMapping("/active")
    public List<AcademicYear> getActive() {
        return service.findActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicYear> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AcademicYear create(@RequestBody AcademicYear obj) {
        return service.save(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicYear> update(@PathVariable UUID id, @RequestBody AcademicYear obj) {
        return service.findById(id)
                .map(existing -> {
                    existing.setCode(obj.getCode());
                    existing.setName(obj.getName());
                    existing.setYear(obj.getYear());
                    existing.setDescription(obj.getDescription());
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