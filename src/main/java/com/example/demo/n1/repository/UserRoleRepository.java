package com.example.demo.n1.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.n1.model.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

    List<UserRole> findByDeletedAtIsNullOrderByCreatedAtDesc();
}

