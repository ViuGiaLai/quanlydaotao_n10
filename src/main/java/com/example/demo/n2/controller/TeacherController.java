package com.example.demo.n2.controller;

import com.example.demo.n2.model.entity.Teacher;
import com.example.demo.n2.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin(origins = "*")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.getAll();
    }

    @GetMapping("/list")
    public List<Teacher> listAllTeachers() {
        return teacherService.getAll();
    }

    @GetMapping("/{id}")
    public Teacher getTeacherById(@PathVariable UUID id) {
        return teacherService.getById(id);
    }

    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherService.create(teacher);
    }

    @PutMapping("/{id}")
    public Teacher updateTeacher(@PathVariable UUID id, @RequestBody Teacher teacher) {
        return teacherService.update(id, teacher);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable UUID id) {
        teacherService.delete(id);
    }
}