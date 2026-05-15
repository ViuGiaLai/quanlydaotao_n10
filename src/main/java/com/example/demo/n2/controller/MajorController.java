package com.example.demo.n2.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.n2.model.entity.Major;
import com.example.demo.n2.service.MajorService;

@RestController
@RequestMapping("/api/majors")
@CrossOrigin(origins = "*")
public class MajorController {

    private final MajorService service;

    public MajorController(MajorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Major> getAll(
            @RequestParam(required = false) UUID departmentId) {
        if (departmentId != null) {
            return service.findByDepartmentId(departmentId);
        }
        return service.findAll();
    }

    @GetMapping("/active")
    public List<Major> getActive() {
        return service.findActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Major> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Major create(@RequestBody Major obj) {
        return service.save(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Major> update(@PathVariable UUID id, @RequestBody Major obj) {
        return service.findById(id)
                .map(existing -> {
                    existing.setCode(obj.getCode());
                    existing.setName(obj.getName());
                    existing.setDepartmentId(obj.getDepartmentId());
                    existing.setDescription(obj.getDescription());
                    existing.setDegreeLevel(obj.getDegreeLevel());
                    existing.setDurationYears(obj.getDurationYears());
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