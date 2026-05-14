package com.example.demo.n10.controller;

import com.example.demo.n10.model.entity.Semester;
import com.example.demo.n10.service.SemesterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/semesters")
@CrossOrigin(origins = "*")
public class SemesterController {

    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @GetMapping({"/", "/list"})
    public List<Semester> getAllSemesters() {
        return semesterService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Semester> getSemesterById(@PathVariable UUID id) {
        return semesterService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Semester createSemester(@RequestBody Semester semester) {
        return semesterService.save(semester);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Semester> updateSemester(@PathVariable UUID id, @RequestBody Semester semester) {
        return semesterService.findById(id)
                .map(existingSemester -> {
                    existingSemester.setSemesterCode(semester.getSemesterCode());
                    existingSemester.setSemesterName(semester.getSemesterName());
                    existingSemester.setDescription(semester.getDescription());
                    existingSemester.setStartDate(semester.getStartDate());
                    existingSemester.setEndDate(semester.getEndDate());
                    existingSemester.setYear(semester.getYear());
                    existingSemester.setTerm(semester.getTerm());
                    existingSemester.setIsActive(semester.getIsActive());
                    return ResponseEntity.ok(semesterService.save(existingSemester));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSemester(@PathVariable UUID id) {
        if (semesterService.findById(id).isPresent()) {
            semesterService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}