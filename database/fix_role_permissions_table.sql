-- Fix bảng role_permissions: bỏ cột id không cần thiết
-- Chạy script này trong SQL Server

-- 1. Xóa bảng cũ nếu có dữ liệu
IF OBJECT_ID('role_permissions', 'U') IS NOT NULL
    DROP TABLE role_permissions;

-- 2. Tạo lại bảng đúng cấu trúc (composite key)
CREATE TABLE role_permissions (
    role_id UNIQUEIDENTIFIER NOT NULL,
    permission_id UNIQUEIDENTIFIER NOT NULL,
    is_active BIT DEFAULT 1,
    created_at DATETIME2 DEFAULT GETDATE(),
    updated_at DATETIME2 NULL,
    created_by UNIQUEIDENTIFIER NULL,
    updated_by UNIQUEIDENTIFIER NULL,
    deleted_at DATETIME2 NULL,
    deleted_by UNIQUEIDENTIFIER NULL,
    PRIMARY KEY (role_id, permission_id)
);

PRINT 'Created role_permissions table successfully';