package com.example.demo.n2.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.n2.model.entity.TrainingProgram;
import com.example.demo.n2.service.TrainingProgramService;

@RestController
@RequestMapping("/api/training-programs")
@CrossOrigin(origins = "*")
public class TrainingProgramController {

    private final TrainingProgramService service;

    public TrainingProgramController(TrainingProgramService service) {
        this.service = service;
    }

    @GetMapping
    public List<TrainingProgram> getAll() {
        return service.findAll();
    }

    @GetMapping("/active")
    public List<TrainingProgram> getActive() {
        return service.findActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingProgram> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TrainingProgram create(@RequestBody TrainingProgram obj) {
        return service.save(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingProgram> update(@PathVariable UUID id, @RequestBody TrainingProgram obj) {
        return service.findById(id)
                .map(existing -> {
                    existing.setCode(obj.getCode());
                    existing.setName(obj.getName());
                    existing.setDescription(obj.getDescription());
                    existing.setDegreeType(obj.getDegreeType());
                    existing.setCreditsRequired(obj.getCreditsRequired());
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