package com.example.demo.n2.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.n2.model.entity.Department;
import com.example.demo.n2.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Department> getAll() {
        return service.findAll();
    }

    @GetMapping("/active")
    public List<Department> getActive() {
        return service.findActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Department create(@RequestBody Department obj) {
        return service.save(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@PathVariable UUID id, @RequestBody Department obj) {
        return service.findById(id)
                .map(existing -> {
                    existing.setCode(obj.getCode());
                    existing.setName(obj.getName());
                    existing.setDescription(obj.getDescription());
                    existing.setDeanName(obj.getDeanName());
                    existing.setOfficeLocation(obj.getOfficeLocation());
                    existing.setPhone(obj.getPhone());
                    existing.setEmail(obj.getEmail());
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