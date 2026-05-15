-- Migration: Add optimistic locking, validation, and published fields to exam_results
-- This migration prevents concurrent modification issues and adds publish workflow

-- Add version column for optimistic locking
ALTER TABLE exam_results
ADD COLUMN version BIGINT DEFAULT 0;

-- Add published state tracking
ALTER TABLE exam_results
ADD COLUMN published_at DATETIME NULL;

ALTER TABLE exam_results
ADD COLUMN published_by CHAR(36) NULL;

-- Add index for audit queries
CREATE INDEX idx_exam_results_status ON exam_results(status);
CREATE INDEX idx_exam_results_edit_status ON exam_results(edit_status);
CREATE INDEX idx_exam_results_published ON exam_results(published_at);
