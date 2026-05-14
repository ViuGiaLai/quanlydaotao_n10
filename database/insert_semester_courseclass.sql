-- =============
-- DỮ LIỆU MẪU: SEMESTER (HỌC KỲ)
-- =============

-- Thêm dữ liệu mẫu cho bảng semesters
INSERT INTO [semesters] (
    id, 
    semester_code, 
    semester_name, 
    description, 
    start_date, 
    end_date, 
    year, 
    term, 
    created_at, 
    updated_at,
    created_by,
    updated_by
) VALUES 
-- Học kỳ I năm 2024-2025
(
    NEWID(), 
    'HK1_2024_2025', 
    'Học Kỳ I Năm 2024-2025', 
    'Kỳ học thứ nhất năm học 2024-2025',
    '2024-09-01', 
    '2024-12-31', 
    2024, 
    1, 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
),
-- Học kỳ II năm 2024-2025
(
    NEWID(), 
    'HK2_2024_2025', 
    'Học Kỳ II Năm 2024-2025', 
    'Kỳ học thứ hai năm học 2024-2025',
    '2025-01-06', 
    '2025-05-30', 
    2024, 
    2, 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
),
-- Học kỳ III (Hè) năm 2024-2025
(
    NEWID(), 
    'HK3_2024_2025', 
    'Học Kỳ III (Hè) Năm 2024-2025', 
    'Kỳ học hè năm 2024-2025',
    '2025-06-02', 
    '2025-08-31', 
    2024, 
    3, 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
);

PRINT 'Inserted sample semester data successfully';

-- =============
-- DỮ LIỆU MẪU: COURSE_CLASS (LỚP HỌC PHẦN)
-- =============

-- Lấy ID của các học kỳ vừa thêm
DECLARE @HK1_ID UNIQUEIDENTIFIER;
DECLARE @HK2_ID UNIQUEIDENTIFIER;
DECLARE @HK3_ID UNIQUEIDENTIFIER;

SET @HK1_ID = (SELECT TOP 1 id FROM semesters WHERE semester_code = 'HK1_2024_2025');
SET @HK2_ID = (SELECT TOP 1 id FROM semesters WHERE semester_code = 'HK2_2024_2025');
SET @HK3_ID = (SELECT TOP 1 id FROM semesters WHERE semester_code = 'HK3_2024_2025');

-- Thêm dữ liệu mẫu cho bảng course_classes
INSERT INTO [course_classes] (
    id, 
    class_code, 
    class_name, 
    description, 
    semester_id, 
    subject_id, 
    teacher_id, 
    max_students, 
    current_students, 
    start_date, 
    end_date, 
    created_at, 
    updated_at,
    created_by,
    updated_by
) VALUES 
-- Lớp Toán Cao Cấp I - HK1
(
    NEWID(), 
    'MATH101-01', 
    'Toán Cao Cấp I - Nhóm 01', 
    'Lớp học phần Toán Cao Cấp I, nhóm thứ nhất',
    @HK1_ID, 
    NULL, 
    NULL, 
    40, 
    38, 
    '2024-09-01', 
    '2024-12-31', 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
),
-- Lớp Toán Cao Cấp I - HK1
(
    NEWID(), 
    'MATH101-02', 
    'Toán Cao Cấp I - Nhóm 02', 
    'Lớp học phần Toán Cao Cấp I, nhóm thứ hai',
    @HK1_ID, 
    NULL, 
    NULL, 
    40, 
    35, 
    '2024-09-01', 
    '2024-12-31', 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
),
-- Lớp Vật Lý Đại Cương - HK1
(
    NEWID(), 
    'PHY102-01', 
    'Vật Lý Đại Cương - Nhóm 01', 
    'Lớp học phần Vật Lý Đại Cương',
    @HK1_ID, 
    NULL, 
    NULL, 
    45, 
    43, 
    '2024-09-02', 
    '2024-12-31', 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
),
-- Lớp Hóa Học Đại Cương - HK1
(
    NEWID(), 
    'CHEM103-01', 
    'Hóa Học Đại Cương - Nhóm 01', 
    'Lớp học phần Hóa Học Đại Cương',
    @HK1_ID, 
    NULL, 
    NULL, 
    40, 
    40, 
    '2024-09-03', 
    '2024-12-31', 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
),
-- Lớp Tiếng Anh I - HK1
(
    NEWID(), 
    'ENGLISH101-01', 
    'Tiếng Anh I - Nhóm 01', 
    'Lớp học phần Tiếng Anh I, nhóm 01',
    @HK1_ID, 
    NULL, 
    NULL, 
    30, 
    28, 
    '2024-09-04', 
    '2024-12-31', 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
),
-- Lớp Tiếng Anh I - HK1
(
    NEWID(), 
    'ENGLISH101-02', 
    'Tiếng Anh I - Nhóm 02', 
    'Lớp học phần Tiếng Anh I, nhóm 02',
    @HK1_ID, 
    NULL, 
    NULL, 
    30, 
    29, 
    '2024-09-04', 
    '2024-12-31', 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
),

-- ===== HỌC KỲ II =====

-- Lớp Toán Cao Cấp II - HK2
(
    NEWID(), 
    'MATH102-01', 
    'Toán Cao Cấp II - Nhóm 01', 
    'Lớp học phần Toán Cao Cấp II, nhóm thứ nhất',
    @HK2_ID, 
    NULL, 
    NULL, 
    40, 
    37, 
    '2025-01-06', 
    '2025-05-30', 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
),
-- Lớp Toán Cao Cấp II - HK2
(
    NEWID(), 
    'MATH102-02', 
    'Toán Cao Cấp II - Nhóm 02', 
    'Lớp học phần Toán Cao Cấp II, nhóm thứ hai',
    @HK2_ID, 
    NULL, 
    NULL, 
    40, 
    34, 
    '2025-01-06', 
    '2025-05-30', 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
),
-- Lớp Kinh Tế Vi Mô - HK2
(
    NEWID(), 
    'ECON201-01', 
    'Kinh Tế Vi Mô - Nhóm 01', 
    'Lớp học phần Kinh Tế Vi Mô',
    @HK2_ID, 
    NULL, 
    NULL, 
    45, 
    42, 
    '2025-01-07', 
    '2025-05-30', 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
),
-- Lớp Tin Học Cơ Bản - HK2
(
    NEWID(), 
    'IT101-01', 
    'Tin Học Cơ Bản - Nhóm 01', 
    'Lớp học phần Tin Học Cơ Bản',
    @HK2_ID, 
    NULL, 
    NULL, 
    35, 
    33, 
    '2025-01-08', 
    '2025-05-30', 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
),
-- Lớp Tiếng Anh II - HK2
(
    NEWID(), 
    'ENGLISH102-01', 
    'Tiếng Anh II - Nhóm 01', 
    'Lớp học phần Tiếng Anh II, nhóm 01',
    @HK2_ID, 
    NULL, 
    NULL, 
    30, 
    28, 
    '2025-01-09', 
    '2025-05-30', 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
),

-- ===== HỌC KỲ III (HÈ) =====

-- Lớp Lập Trình C++ - HK3
(
    NEWID(), 
    'PROG301-01', 
    'Lập Trình C++ - Nhóm 01', 
    'Lớp học phần Lập Trình C++',
    @HK3_ID, 
    NULL, 
    NULL, 
    40, 
    38, 
    '2025-06-02', 
    '2025-08-31', 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
),
-- Lớp Cơ Sở Dữ Liệu - HK3
(
    NEWID(), 
    'DB301-01', 
    'Cơ Sở Dữ Liệu - Nhóm 01', 
    'Lớp học phần Cơ Sở Dữ Liệu',
    @HK3_ID, 
    NULL, 
    NULL, 
    35, 
    32, 
    '2025-06-03', 
    '2025-08-31', 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
),
-- Lớp Web Development - HK3
(
    NEWID(), 
    'WEB401-01', 
    'Web Development - Nhóm 01', 
    'Lớp học phần Web Development',
    @HK3_ID, 
    NULL, 
    NULL, 
    30, 
    28, 
    '2025-06-04', 
    '2025-08-31', 
    GETDATE(), 
    GETDATE(),
    NULL,
    NULL
);

PRINT 'Inserted sample course class data successfully';

-- =============
-- KIỂM TRA DỮ LIỆU VỪA THÊM
-- =============

PRINT '==================';
PRINT 'THỐNG KÊ HỌC KỲ (SEMESTER)';
PRINT '==================';
SELECT 
    'Tổng số học kỳ' AS [Thống kê], 
    COUNT(*) AS [Số lượng]
FROM semesters;

PRINT '';
PRINT '==================';
PRINT 'DANH SÁCH HỌC KỲ';
PRINT '==================';
SELECT 
    semester_code AS [Mã HK],
    semester_name AS [Tên Học Kỳ],
    year AS [Năm],
    term AS [Kỳ],
    CONVERT(VARCHAR(10), start_date, 103) AS [Ngày bắt đầu],
    CONVERT(VARCHAR(10), end_date, 103) AS [Ngày kết thúc]
FROM semesters
ORDER BY year DESC, term DESC;

PRINT '';
PRINT '==================';
PRINT 'THỐNG KÊ LỚP HỌC PHẦN (COURSE CLASS)';
PRINT '==================';
SELECT 
    'Tổng số lớp học phần' AS [Thống kê], 
    COUNT(*) AS [Số lượng]
FROM course_classes;

PRINT '';
PRINT '==================';
PRINT 'DANH SÁCH LỚP HỌC PHẦN';
PRINT '==================';
SELECT 
    cc.class_code AS [Mã Lớp],
    cc.class_name AS [Tên Lớp],
    s.semester_code AS [Mã HK],
    cc.max_students AS [Sức chứa],
    cc.current_students AS [Số hiện tại],
    CONVERT(VARCHAR(10), cc.start_date, 103) AS [Bắt đầu],
    CONVERT(VARCHAR(10), cc.end_date, 103) AS [Kết thúc]
FROM course_classes cc
LEFT JOIN semesters s ON cc.semester_id = s.id
ORDER BY s.year DESC, s.term DESC, cc.class_code;
