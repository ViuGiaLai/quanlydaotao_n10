package com.example.demo.n1.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.n1.model.entity.UserRole;
import com.example.demo.n1.repository.RoleRepository;
import com.example.demo.n1.repository.UserRepository;
import com.example.demo.n1.repository.UserRoleRepository;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository,
            UserRepository userRepository,
            RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<UserRole> findAllActive() {
        return userRoleRepository.findByDeletedAtIsNullOrderByCreatedAtDesc();
    }

    @Transactional
    public UserRole create(UUID userId, UUID roleId, Boolean isActive) {
        userRepository.findById(userId).orElseThrow();
        roleRepository.findById(roleId).orElseThrow();

        UserRole ur = new UserRole();
        ur.setUserId(userId);
        ur.setRoleId(roleId);
        ur.setIsActive(isActive != null ? isActive : Boolean.TRUE);
        ur.setCreatedAt(LocalDateTime.now());
        ur.setUpdatedAt(LocalDateTime.now());
        return userRoleRepository.save(ur);
    }

    @Transactional
    public UserRole update(UUID id, UUID userId, UUID roleId, Boolean isActive) {
        UserRole ur = userRoleRepository.findById(id).orElseThrow();

        if (userId != null) {
            userRepository.findById(userId).orElseThrow();
            ur.setUserId(userId);
        }
        if (roleId != null) {
            roleRepository.findById(roleId).orElseThrow();
            ur.setRoleId(roleId);
        }
        if (isActive != null) {
            ur.setIsActive(isActive);
        }
        ur.setUpdatedAt(LocalDateTime.now());
        return userRoleRepository.save(ur);
    }

    @Transactional
    public void softDelete(UUID id) {
        UserRole ur = userRoleRepository.findById(id).orElseThrow();
        ur.setDeletedAt(LocalDateTime.now());
        ur.setIsActive(Boolean.FALSE);
        userRoleRepository.save(ur);
    }
}

