-- Bảng trung gian: Lớp học - Môn học (1 lớp có nhiều môn)
CREATE TABLE course_class_subjects (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    course_class_id UNIQUEIDENTIFIER NOT NULL,
    subject_id UNIQUEIDENTIFIER NOT NULL,
    teacher_id UNIQUEIDENTIFIER,
    
    CONSTRAINT fk_ccs_course_class FOREIGN KEY (course_class_id) REFERENCES course_classes(id),
    CONSTRAINT fk_ccs_subject FOREIGN KEY (subject_id) REFERENCES subjects(id),
    CONSTRAINT fk_ccs_teacher FOREIGN KEY (teacher_id) REFERENCES users(id)
);

-- Index để tìm kiếm nhanh
CREATE INDEX idx_ccs_course_class ON course_class_subjects(course_class_id);
CREATE INDEX idx_ccs_subject ON course_class_subjects(subject_id);