package com.example.demo.n10.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "exam_rooms")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ExamRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; // STT 1: id

    @Column(name = "exam_id", nullable = false)
    private UUID examId; // STT 2: exam_id

    @Column(name = "room_id", nullable = false)
    private UUID roomId; // STT 3: room_id

    private Integer capacity; // STT 4: capacity

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // STT 5: created_at

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // STT 6: updated_at

    @Column(name = "created_by")
    private UUID createdBy; // STT 7: created_by

    @Column(name = "updated_by")
    private UUID updatedBy; // STT 8: updated_by

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // STT 9: deleted_at

    @Column(name = "deleted_by")
    private UUID deletedBy; // STT 10: deleted_by

    @Column(name = "is_active")
    private Boolean isActive = true; // STT 11: is_active
    
    // Lưu ý: Nếu Huy muốn dùng tên phòng như đã thảo luận trước đó, 
    // bạn có thể thêm trường này nhưng không thuộc danh sách 11 trường gốc.
    @Column(name = "exam_room_name")
    private String examRoomName; 
}