package com.example.demo.n1.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.n1.model.entity.Role;
import com.example.demo.n1.service.RoleService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/roles")
public class RoleApiController {

    private final RoleService roleService;

    public RoleApiController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public Role create(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @GetMapping
    public List<Role> list() {
        return roleService.findAll();
    }

    @GetMapping("/list")
    public List<Role> listAll() {
        return roleService.findAll();
    }

    @PostMapping("/{roleId}/permissions/{permId}")
    public Role addPermission(@PathVariable UUID roleId,
                              @PathVariable UUID permId) {
        return roleService.addPermission(roleId, permId);
    }

    @PostMapping("/{id}")
    public Role update(@PathVariable UUID id, @RequestBody Role role) {
        return roleService.updateRole(id, role);
    }

    @PostMapping("/{id}/delete")
    public void delete(@PathVariable UUID id) {
        roleService.deleteRole(id);
    }
}