package com.example.demo.n2.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.example.demo.n2.model.entity.ImportLog;
import com.example.demo.n2.service.ImportLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/import-logs")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ImportLogController {
    
    private final ImportLogService importLogService;
    
    @GetMapping
    public List<ImportLog> list() {
        return importLogService.findAll();
    }
    
    @GetMapping("/{module}")
    public List<ImportLog> listByModule(@PathVariable String module) {
        return importLogService.findByModule(module);
    }
    
    @GetMapping("/{id}/errors")
    public ImportLog getErrors(@PathVariable UUID id) {
        return importLogService.findAll().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    @PostMapping
    public ImportLog create(@RequestBody ImportLog importLog) {
        return importLogService.save(importLog);
    }
}