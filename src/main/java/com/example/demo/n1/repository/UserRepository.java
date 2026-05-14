package com.example.demo.n1.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.n1.model.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByUsername(String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username AND u.deletedAt IS NULL")
    Optional<User> findFirstByUsernameIgnoreCaseAndDeletedAtIsNull(@Param("username") String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email AND u.deletedAt IS NULL")
    Optional<User> findFirstByEmailIgnoreCaseAndDeletedAtIsNull(@Param("email") String email);
}
