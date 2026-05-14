-- Tạo bảng teachers
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'teachers')
BEGIN
    CREATE TABLE teachers (
        id UNIQUEIDENTIFIER PRIMARY KEY,
        user_id UNIQUEIDENTIFIER,
        code NVARCHAR(20),
        full_name NVARCHAR(100),
        date_of_birth DATETIME2,
        gender NVARCHAR(10),
        email NVARCHAR(100),
        phone NVARCHAR(20),
        degree NVARCHAR(50),
        academic_title NVARCHAR(50),
        specialization NVARCHAR(100),
        department_id UNIQUEIDENTIFIER,
        status NVARCHAR(50),
        is_active BIT DEFAULT 1,
        created_at DATETIME2,
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        deleted_at DATETIME2,
        deleted_by UNIQUEIDENTIFIER
    );
    PRINT 'Table teachers created successfully';
END
ELSE
BEGIN
    PRINT 'Table teachers already exists';
END
GO

-- Tạo bảng students
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'students')
BEGIN
    CREATE TABLE students (
        id UNIQUEIDENTIFIER PRIMARY KEY,
        user_id UNIQUEIDENTIFIER,
        code NVARCHAR(20),
        full_name NVARCHAR(100),
        date_of_birth DATETIME2,
        gender NVARCHAR(10),
        personal_identification_number NVARCHAR(20),
        date_of_issue DATETIME2,
        card_place NVARCHAR(100),
        address NVARCHAR(300),
        current_address NVARCHAR(300),
        academic_year_year UNIQUEIDENTIFIER,
        department_id UNIQUEIDENTIFIER,
        major_id UNIQUEIDENTIFIER,
        training_program_id UNIQUEIDENTIFIER,
        status NVARCHAR(50),
        student_classe_id UNIQUEIDENTIFIER,
        admission_year DATETIME2,
        is_active BIT DEFAULT 1,
        created_at DATETIME2,
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        deleted_at DATETIME2,
        deleted_by UNIQUEIDENTIFIER
    );
    PRINT 'Table students created successfully';
END
ELSE
BEGIN
    PRINT 'Table students already exists';
END
GO