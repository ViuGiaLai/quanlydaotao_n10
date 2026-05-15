package com.example.demo.n2.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.n2.model.entity.ImportLog;

@Repository
public interface ImportLogRepository extends JpaRepository<ImportLog, UUID> {
    
    List<ImportLog> findByModuleAndIsDeletedFalseOrderByImportedAtDesc(String module);
    
    List<ImportLog> findByIsDeletedFalseOrderByImportedAtDesc();
}