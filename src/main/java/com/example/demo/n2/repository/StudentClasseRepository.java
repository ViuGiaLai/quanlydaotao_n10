package com.example.demo.n2.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.n2.model.entity.StudentClasse;

@Repository
public interface StudentClasseRepository extends JpaRepository<StudentClasse, UUID> {
    
    @Query("SELECT sc FROM StudentClasse sc WHERE sc.code = :code AND sc.deletedAt IS NULL")
    Optional<StudentClasse> findByCode(@Param("code") String code);
    
    @Query("SELECT sc FROM StudentClasse sc WHERE sc.departmentId = :departmentId AND sc.deletedAt IS NULL")
    List<StudentClasse> findByDepartmentId(@Param("departmentId") UUID departmentId);
    
    @Query("SELECT sc FROM StudentClasse sc WHERE sc.majorId = :majorId AND sc.deletedAt IS NULL")
    List<StudentClasse> findByMajorId(@Param("majorId") UUID majorId);
    
    @Query("SELECT sc FROM StudentClasse sc WHERE sc.employeeId = :employeeId AND sc.deletedAt IS NULL")
    List<StudentClasse> findByEmployeeId(@Param("employeeId") UUID employeeId);
}