-- =====
-- Fix user_roles table - Add unique constraint
-- =====

-- First, clean up existing duplicates
;WITH DuplicateCTE AS (
    SELECT 
        id,
        ROW_NUMBER() OVER (PARTITION BY user_id, role_id ORDER BY created_at) AS rn
    FROM user_roles
    WHERE deleted_at IS NULL
)
DELETE FROM DuplicateCTE WHERE rn > 1;

-- Add unique constraint if not exists
IF NOT EXISTS (
    SELECT * FROM sys.key_constraints 
    WHERE name = 'UQ_user_roles' AND type = 'UQ'
)
BEGIN
    ALTER TABLE user_roles
    ADD CONSTRAINT UQ_user_roles UNIQUE (user_id, role_id);
    PRINT N'Đã thêm unique constraint UQ_user_roles';
END
ELSE
BEGIN
    PRINT N'Unique constraint đã tồn tại';
END
GO

PRINT N'Đã dọn dẹp duplicate trong user_roles';
GO