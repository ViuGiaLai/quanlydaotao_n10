-- Thêm cột edit_status vào bảng exam_results
-- Chạy script này để thêm cột mới cho phép Admin mở/khóa nhập điểm

-- SQL Server
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'exam_results' AND COLUMN_NAME = 'edit_status')
BEGIN
    ALTER TABLE exam_results ADD edit_status NVARCHAR(10) NULL;
    PRINT 'Column edit_status added successfully';
END
ELSE
BEGIN
    PRINT 'Column edit_status already exists';
END
GO

-- Cập nhật giá trị mặc định cho các record hiện tại (nếu cột is_editable tồn tại)
IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'exam_results' AND COLUMN_NAME = 'is_editable')
BEGIN
    -- Copy giá trị từ is_editable sang edit_status
    UPDATE exam_results 
    SET edit_status = CASE WHEN is_editable = 1 THEN 'OPEN' ELSE 'LOCKED' END
    WHERE edit_status IS NULL;
    
    PRINT 'Data migrated from is_editable to edit_status';
END
GO