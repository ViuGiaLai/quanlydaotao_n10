-- Create import_logs table
CREATE TABLE IF NOT EXISTS import_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    module VARCHAR(100),
    filename VARCHAR(255),
    total_rows INTEGER,
    success_rows INTEGER,
    error_rows INTEGER,
    imported_by VARCHAR(100),
    imported_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    error_details TEXT,
    is_deleted BOOLEAN DEFAULT FALSE
);

-- Add index for faster queries
CREATE INDEX IF NOT EXISTS idx_import_logs_module ON import_logs(module);
CREATE INDEX IF NOT EXISTS idx_import_logs_imported_at ON import_logs(imported_at);