package com.example.demo.n1.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.n1.model.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
}
