package com.example.demo.n10.controller;

import com.example.demo.n10.model.entity.CourseClass;
import com.example.demo.n10.service.CourseClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/course-classes")
@CrossOrigin(origins = "*")
public class CourseClassController {

    private final CourseClassService courseClassService;

    public CourseClassController(CourseClassService courseClassService) {
        this.courseClassService = courseClassService;
    }

    @GetMapping({"/", "/list"})
    public List<CourseClass> getAllCourseClasses() {
        return courseClassService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseClass> getCourseClassById(@PathVariable UUID id) {
        return courseClassService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CourseClass createCourseClass(@RequestBody CourseClass courseClass) {
        return courseClassService.save(courseClass);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseClass> updateCourseClass(@PathVariable UUID id, @RequestBody CourseClass courseClass) {
        return courseClassService.findById(id)
                .map(existingCourseClass -> {
                    existingCourseClass.setClassCode(courseClass.getClassCode());
                    existingCourseClass.setClassName(courseClass.getClassName());
                    existingCourseClass.setDescription(courseClass.getDescription());
                    existingCourseClass.setSemesterId(courseClass.getSemesterId());
                    existingCourseClass.setSubjectId(courseClass.getSubjectId());
                    existingCourseClass.setTeacherId(courseClass.getTeacherId());
                    existingCourseClass.setMaxStudents(courseClass.getMaxStudents());
                    existingCourseClass.setCurrentStudents(courseClass.getCurrentStudents());
                    existingCourseClass.setStartDate(courseClass.getStartDate());
                    existingCourseClass.setEndDate(courseClass.getEndDate());
                    existingCourseClass.setIsActive(courseClass.getIsActive());
                    return ResponseEntity.ok(courseClassService.save(existingCourseClass));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseClass(@PathVariable UUID id) {
        if (courseClassService.findById(id).isPresent()) {
            courseClassService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}