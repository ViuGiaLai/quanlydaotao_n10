-- Tạo bảng exam_rooms
CREATE TABLE IF NOT EXISTS exam_rooms (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    exam_id UUID NOT NULL,
    room_id UUID,
    exam_room_name VARCHAR(255),
    capacity INTEGER,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    deleted_at TIMESTAMP,
    deleted_by UUID
);

-- Comment
COMMENT ON TABLE exam_rooms IS 'Phòng thi - ánh xạ kỳ thi với phòng học';