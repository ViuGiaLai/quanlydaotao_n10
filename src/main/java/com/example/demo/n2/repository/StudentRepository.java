package com.example.demo.n2.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.n2.model.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Query("SELECT s FROM Student s WHERE s.user_id = :userId AND s.deletedAt IS NULL")
    Optional<Student> findByUser_Id(@Param("userId") UUID userId);
    
    @Query("SELECT s FROM Student s WHERE s.code = :code AND s.deletedAt IS NULL")
    Optional<Student> findByCode(@Param("code") String code);
}