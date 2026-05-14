package com.example.demo.n1.controller;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.n1.model.entity.Permission;
import com.example.demo.n1.repository.PermissionRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@RestController
@RequestMapping("/api/permissions")
public class PermissionApiController {

    private final PermissionRepository repo;

    public PermissionApiController(PermissionRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Permission create(@RequestBody Permission p) {
        return repo.save(p);
    }

    @GetMapping
    public List<Permission> list() {
        return repo.findAll();
    }

    @GetMapping("/list")
    public List<Permission> listAll() {
        return repo.findAll();
    }

    @PutMapping("/{id}")
    public Permission update(@PathVariable UUID id, @RequestBody Permission p) {
        p.setId(id);
        return repo.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        repo.deleteById(id);
    }
}