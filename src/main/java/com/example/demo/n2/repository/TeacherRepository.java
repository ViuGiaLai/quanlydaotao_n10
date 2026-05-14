package com.example.demo.n2.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.n2.model.entity.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    @Query("SELECT t FROM Teacher t WHERE t.userId = :userId AND t.deletedAt IS NULL")
    Optional<Teacher> findByUser_Id(@Param("userId") UUID userId);
    
    @Query("SELECT t FROM Teacher t WHERE t.code = :code AND t.deletedAt IS NULL")
    Optional<Teacher> findByCode(@Param("code") String code);
}