package com.example.demo.n2.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.n2.model.entity.AdvisorClasseSection;

@Repository
public interface AdvisorClasseSectionRepository extends JpaRepository<AdvisorClasseSection, UUID> {
    
    @Query("SELECT acs FROM AdvisorClasseSection acs WHERE acs.employeeId = :employeeId AND acs.deletedAt IS NULL")
    List<AdvisorClasseSection> findByEmployeeId(@Param("employeeId") UUID employeeId);
    
    @Query("SELECT acs FROM AdvisorClasseSection acs WHERE acs.studentClasseId = :studentClasseId AND acs.deletedAt IS NULL")
    List<AdvisorClasseSection> findByStudentClasseId(@Param("studentClasseId") UUID studentClasseId);
}