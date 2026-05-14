package com.example.demo.n1.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.n1.model.entity.Role;
import com.example.demo.n1.model.entity.User;
import com.example.demo.n1.repository.RoleRepository;
import com.example.demo.n1.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepo;

    private final RoleRepository roleRepo;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username, String password, String email, String phone, String avatarUrl) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        u.setEmail(email);
        u.setPhone(phone);
        u.setAvatarUrl(avatarUrl);

        return userRepo.save(u);
    }


    // public User createUser(String username, String password) {
    //     User u = new User();
    //     u.setUsername(username);
    //     u.setPassword(password);
    //     // u.setPassword(encoder.encode(password));

    //     return userRepo.save(u);
    // }

    public User assignRole(UUID userId, UUID roleId) {
        User u = userRepo.findById(userId).orElseThrow();
        Role r = roleRepo.findById(roleId).orElseThrow();
        u.getRoles().add(r);
        return userRepo.save(u);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }
<<<<<<< HEAD
=======

    public User updateUser(UUID id, String username, String password, String email, String phone, String avatarUrl) {
        User existing = userRepo.findById(id).orElseThrow();
        existing.setUsername(username);
        existing.setPassword(passwordEncoder.encode(password));
        existing.setEmail(email);
        existing.setPhone(phone);
        existing.setAvatarUrl(avatarUrl);
        return userRepo.save(existing);
    }

    @Transactional
    public void deleteUser(UUID id) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            user.setDeletedAt(LocalDateTime.now());
            user.setIsActive(false);
            userRepo.save(user);
        }
    }
>>>>>>> 0d2f095 (update new UI viu)
}

