-- =============
-- TẠO BẢNG ACADEMIC_YEARS (NIÊN HỌC)
-- =============

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'academic_years')
BEGIN
    CREATE TABLE academic_years (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        year_code NVARCHAR(20) NOT NULL,
        year_name NVARCHAR(100) NOT NULL,
        start_year INT NOT NULL,
        end_year INT NOT NULL,
        is_active BIT DEFAULT 1,
        created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        updated_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        deleted_at DATETIME2,
        deleted_by UNIQUEIDENTIFIER
    );
    PRINT 'Table academic_years created successfully';
END
ELSE
BEGIN
    PRINT 'Table academic_years already exists';
END
GO

-- =============
-- TẠO BẢNG DEPARTMENTS (KHOA)
-- =============

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'departments')
BEGIN
    CREATE TABLE departments (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        department_code NVARCHAR(50) NOT NULL,
        department_name NVARCHAR(200) NOT NULL,
        description NVARCHAR(MAX),
        dean_name NVARCHAR(100),
        office_location NVARCHAR(200),
        phone NVARCHAR(20),
        email NVARCHAR(100),
        is_active BIT DEFAULT 1,
        created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        updated_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        deleted_at DATETIME2,
        deleted_by UNIQUEIDENTIFIER
    );
    PRINT 'Table departments created successfully';
END
ELSE
BEGIN
    PRINT 'Table departments already exists';
END
GO

-- =============
-- TẠO BẢNG MAJORS (NGÀNH)
-- =============

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'majors')
BEGIN
    CREATE TABLE majors (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        major_code NVARCHAR(50) NOT NULL,
        major_name NVARCHAR(200) NOT NULL,
        department_id UNIQUEIDENTIFIER,
        description NVARCHAR(MAX),
        degree_level NVARCHAR(50),
        duration_years INT,
        is_active BIT DEFAULT 1,
        created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        updated_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        deleted_at DATETIME2,
        deleted_by UNIQUEIDENTIFIER,
        CONSTRAINT fk_major_department FOREIGN KEY (department_id) REFERENCES departments(id)
    );
    PRINT 'Table majors created successfully';
    
    CREATE INDEX IX_majors_department_id ON majors(department_id);
END
ELSE
BEGIN
    PRINT 'Table majors already exists';
END
GO

-- =============
-- TẠO BẢNG TRAINING_PROGRAMS (CHƯƠNG TRÌNH ĐÀO TẠO)
-- =============

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'training_programs')
BEGIN
    CREATE TABLE training_programs (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        program_code NVARCHAR(50) NOT NULL,
        program_name NVARCHAR(200) NOT NULL,
        description NVARCHAR(MAX),
        degree_type NVARCHAR(50),
        credits_required INT,
        duration_years INT,
        is_active BIT DEFAULT 1,
        created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        updated_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        deleted_at DATETIME2,
        deleted_by UNIQUEIDENTIFIER
    );
    PRINT 'Table training_programs created successfully';
END
ELSE
BEGIN
    PRINT 'Table training_programs already exists';
END
GO

PRINT 'Lookup tables creation completed!';