-- Tạo bảng exam_types
CREATE TABLE IF NOT EXISTS exam_types (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(MAX),
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    created_by UUID,
    updated_by UUID,
    deleted_at DATETIME2,
    deleted_by UUID,
    is_active BIT NOT NULL DEFAULT 1
);

COMMENT ON TABLE exam_types IS 'Loại kỳ thi';