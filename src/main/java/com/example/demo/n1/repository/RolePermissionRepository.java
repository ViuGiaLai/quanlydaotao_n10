package com.example.demo.n1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.n1.model.entity.RolePermission;
import com.example.demo.n1.model.entity.RolePermissionId;

import java.util.UUID;
import java.util.List;

public interface RolePermissionRepository 
        extends JpaRepository<RolePermission, RolePermissionId> {

    List<RolePermission> findByRoleId(UUID roleId);

    void deleteByRoleIdAndPermissionId(UUID roleId, UUID permissionId);
}