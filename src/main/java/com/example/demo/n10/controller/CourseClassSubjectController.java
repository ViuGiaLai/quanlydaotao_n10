package com.example.demo.n10.controller;

import com.example.demo.n10.model.entity.CourseClassSubject;
import com.example.demo.n10.repository.CourseClassSubjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/course-class-subjects")
@CrossOrigin(origins = "*")
public class CourseClassSubjectController {

    private final CourseClassSubjectRepository repository;

    public CourseClassSubjectController(CourseClassSubjectRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CourseClassSubject> getByCourseClassId(
            @RequestParam UUID courseClassId) {
        return repository.findByCourseClassId(courseClassId);
    }

    @PostMapping
    public CourseClassSubject create(@RequestBody CourseClassSubject courseClassSubject) {
        return repository.save(courseClassSubject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseClassSubject> update(@PathVariable UUID id, @RequestBody CourseClassSubject courseClassSubject) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setTeacherId(courseClassSubject.getTeacherId());
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}