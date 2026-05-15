package com.example.demo.n2.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "import_logs")
@Data
public class ImportLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "module")
    private String module; // students, teachers, users, etc.
    
    @Column(name = "filename")
    private String filename;
    
    @Column(name = "total_rows")
    private Integer totalRows;
    
    @Column(name = "success_rows")
    private Integer successRows;
    
    @Column(name = "error_rows")
    private Integer errorRows;
    
    @Column(name = "imported_by")
    private String importedBy;
    
    @Column(name = "imported_at")
    private LocalDateTime importedAt;
    
    @Column(name = "error_details", columnDefinition = "TEXT")
    private String errorDetails; // JSON array of error rows
    
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    
    @PrePersist
    public void prePersist() {
        if (importedAt == null) {
            importedAt = LocalDateTime.now();
        }
    }
}