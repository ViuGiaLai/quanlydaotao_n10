-- Create audit log table for exam result changes
-- Required for: dispute resolution, compliance, change tracking

CREATE TABLE exam_result_audit_logs (
    id CHAR(36) PRIMARY KEY,
    exam_result_id CHAR(36) NOT NULL,
    changed_by CHAR(36) NOT NULL COMMENT '用户ID who made the change',
    changed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    field_name VARCHAR(50) NOT NULL COMMENT 'e.g., attendance_score, status, edit_status',
    old_value VARCHAR(255) NULL,
    new_value VARCHAR(255) NULL,
    change_reason VARCHAR(255) NULL COMMENT 'Optional: why was this changed?',
    ip_address VARCHAR(45) NULL COMMENT 'IP address of the change',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_exam_result_id (exam_result_id),
    INDEX idx_changed_by (changed_by),
    INDEX idx_changed_at (changed_at),
    FOREIGN KEY (exam_result_id) REFERENCES exam_results(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Add trigger to automatically log changed_at (optional, but helps with data quality)
-- Note: You may want to handle audit logging in the application instead
