package com.example.demo.n1.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.n1.model.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

    List<UserRole> findByDeletedAtIsNullOrderByCreatedAtDesc();
    
    @Query("SELECT ur FROM UserRole ur WHERE ur.userId = :userId AND ur.roleId = :roleId AND ur.deletedAt IS NULL")
    Optional<UserRole> findByUserIdAndRoleId(@Param("userId") UUID userId, @Param("roleId") UUID roleId);
    
    @Query("SELECT ur FROM UserRole ur WHERE ur.userId = :userId AND ur.deletedAt IS NULL")
    List<UserRole> findByUserId(@Param("userId") UUID userId);
}

