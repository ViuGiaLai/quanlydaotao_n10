package com.example.demo.n2.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.n2.model.entity.StudentClasse;
import com.example.demo.n2.service.StudentClasseService;

@RestController
@RequestMapping("/api/student-classes")
@CrossOrigin(origins = "*")
public class StudentClasseController {

    private final StudentClasseService studentClasseService;

    public StudentClasseController(StudentClasseService studentClasseService) {
        this.studentClasseService = studentClasseService;
    }

    @GetMapping
    public List<StudentClasse> getAll(
            @RequestParam(required = false) UUID departmentId,
            @RequestParam(required = false) UUID majorId) {
        if (departmentId != null) {
            return studentClasseService.findByDepartmentId(departmentId);
        }
        if (majorId != null) {
            return studentClasseService.findByMajorId(majorId);
        }
        return studentClasseService.findAll();
    }

    @GetMapping("/list")
    public List<StudentClasse> listAll() {
        return studentClasseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentClasse> getById(@PathVariable UUID id) {
        return studentClasseService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public StudentClasse create(@RequestBody StudentClasse studentClasse) {
        return studentClasseService.save(studentClasse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentClasse> update(@PathVariable UUID id, @RequestBody StudentClasse studentClasse) {
        return studentClasseService.findById(id)
                .map(existing -> {
                    existing.setCode(studentClasse.getCode());
                    existing.setName(studentClasse.getName());
                    existing.setAcademicYearId(studentClasse.getAcademicYearId());
                    existing.setDepartmentId(studentClasse.getDepartmentId());
                    existing.setMajorId(studentClasse.getMajorId());
                    existing.setTrainingProgramId(studentClasse.getTrainingProgramId());
                    existing.setEmployeeId(studentClasse.getEmployeeId());
                    existing.setIsActive(studentClasse.getIsActive());
                    return ResponseEntity.ok(studentClasseService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (studentClasseService.findById(id).isPresent()) {
            studentClasseService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}