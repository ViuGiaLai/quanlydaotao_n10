package com.example.demo.n1.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.n1.model.entity.UserRole;
import com.example.demo.n1.service.UserRoleService;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleApiController {

    private final UserRoleService userRoleService;

    public UserRoleApiController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping
    public List<UserRole> list() {
        return userRoleService.findAllActive();
    }

    @PostMapping
    public UserRole create(@RequestBody UpsertUserRoleRequest req) {
        return userRoleService.create(req.userId(), req.roleId(), req.isActive());
    }

    @PutMapping("/{id}")
    public UserRole update(@PathVariable UUID id, @RequestBody UpsertUserRoleRequest req) {
        return userRoleService.update(id, req.userId(), req.roleId(), req.isActive());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userRoleService.softDelete(id);
    }

    public record UpsertUserRoleRequest(UUID userId, UUID roleId, Boolean isActive) {}
}

