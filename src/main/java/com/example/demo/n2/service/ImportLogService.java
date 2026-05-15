package com.example.demo.n2.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.n2.model.entity.ImportLog;
import com.example.demo.n2.repository.ImportLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImportLogService {
    
    private final ImportLogRepository importLogRepo;
    
    public List<ImportLog> findAll() {
        return importLogRepo.findByIsDeletedFalseOrderByImportedAtDesc();
    }
    
    public List<ImportLog> findByModule(String module) {
        return importLogRepo.findByModuleAndIsDeletedFalseOrderByImportedAtDesc(module);
    }
    
    @Transactional
    public ImportLog save(ImportLog importLog) {
        return importLogRepo.save(importLog);
    }
    
    @Transactional
    public void delete(UUID id) {
        ImportLog log = importLogRepo.findById(id).orElse(null);
        if (log != null) {
            log.setIsDeleted(true);
            importLogRepo.save(log);
        }
    }
}