package com.example.demo.n1.repository;
import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.n1.model.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByUsername(String username);
}
