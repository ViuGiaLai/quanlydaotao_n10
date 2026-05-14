-- =============
-- TẠO BẢNG SEMESTER (HỌC KỲ)
-- =============

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'semesters')
BEGIN
    CREATE TABLE semesters (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        semester_code NVARCHAR(50) NOT NULL,
        semester_name NVARCHAR(100) NOT NULL,
        description NVARCHAR(MAX),
        start_date DATETIME2 NOT NULL,
        end_date DATETIME2 NOT NULL,
        [year] INT NOT NULL,
        term INT NOT NULL,
        is_active BIT DEFAULT 1,
        created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        updated_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        deleted_at DATETIME2,
        deleted_by UNIQUEIDENTIFIER
    );
    PRINT 'Table semesters created successfully';
END
ELSE
BEGIN
    PRINT 'Table semesters already exists';
END
GO

-- =============
-- TẠO BẢNG COURSE_CLASSES (LỚP HỌC PHẦN)
-- =============

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'course_classes')
BEGIN
    CREATE TABLE course_classes (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        class_code NVARCHAR(50) NOT NULL,
        class_name NVARCHAR(100) NOT NULL,
        description NVARCHAR(MAX),
        semester_id UNIQUEIDENTIFIER,
        subject_id UNIQUEIDENTIFIER,
        teacher_id UNIQUEIDENTIFIER,
        max_students INT,
        current_students INT DEFAULT 0,
        start_date DATETIME2,
        end_date DATETIME2,
        is_active BIT DEFAULT 1,
        created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        updated_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        deleted_at DATETIME2,
        deleted_by UNIQUEIDENTIFIER
    );
    PRINT 'Table course_classes created successfully';
    
    -- Tạo index
    CREATE INDEX IX_course_classes_semester_id ON course_classes(semester_id);
    CREATE INDEX IX_course_classes_subject_id ON course_classes(subject_id);
    CREATE INDEX IX_course_classes_teacher_id ON course_classes(teacher_id);
END
ELSE
BEGIN
    PRINT 'Table course_classes already exists';
END
GO

PRINT 'Database tables creation completed!';
