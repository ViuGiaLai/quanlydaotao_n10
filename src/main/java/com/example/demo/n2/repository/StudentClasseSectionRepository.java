package com.example.demo.n2.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.n2.model.entity.StudentClasseSection;

@Repository
public interface StudentClasseSectionRepository extends JpaRepository<StudentClasseSection, UUID> {
    
    @Query("SELECT scs FROM StudentClasseSection scs WHERE scs.studentId = :studentId AND scs.deletedAt IS NULL")
    List<StudentClasseSection> findByStudentId(@Param("studentId") UUID studentId);
    
    @Query("SELECT scs FROM StudentClasseSection scs WHERE scs.studentClasseId = :studentClasseId AND scs.deletedAt IS NULL")
    List<StudentClasseSection> findByStudentClasseId(@Param("studentClasseId") UUID studentClasseId);
    
    @Query("SELECT scs FROM StudentClasseSection scs WHERE scs.studentId = :studentId AND scs.status = :status AND scs.deletedAt IS NULL")
    Optional<StudentClasseSection> findByStudentIdAndStatus(@Param("studentId") UUID studentId, @Param("status") String status);
}