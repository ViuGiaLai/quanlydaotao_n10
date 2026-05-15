package com.example.demo.n10.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.n10.dto.ExamRegistrationDTO;
import com.example.demo.n10.model.entity.ExamRegistration;
import com.example.demo.n10.repository.ExamRegistrationRepository;

@Service
public class ExamRegistrationService {
    
    private static final Logger logger = LoggerFactory.getLogger(ExamRegistrationService.class);
    private final ExamRegistrationRepository examRegistrationRepository;

    public ExamRegistrationService(ExamRegistrationRepository examRegistrationRepository) {
        this.examRegistrationRepository = examRegistrationRepository;
    }

    // Hiển thị tất cả đăng ký thi
    public List<ExamRegistration> findAll() {
        return examRegistrationRepository.findAll();
    }
    
    // Hiển thị tất cả với thông tin chi tiết (DTO)
    public List<ExamRegistrationDTO> findAllWithDetails() {
        List<Object[]> rows = examRegistrationRepository.findAllWithDetails();
        return rows.stream().map(this::mapToDTO).toList();
    }
    
    // Map Object[] sang DTO
    private ExamRegistrationDTO mapToDTO(Object[] row) {
        ExamRegistrationDTO dto = new ExamRegistrationDTO();
        dto.setId(toUUID(row[0]));
        dto.setExamId(toUUID(row[1]));
        dto.setExamRoomId(toUUID(row[2]));
        dto.setStudentId(toUUID(row[3]));
        dto.setRollNumber(toString(row[4]));
        dto.setIsActive(toBoolean(row[5]));
        dto.setExamName(toString(row[6]));
        dto.setRoomName(toString(row[7]));
        dto.setStudentName(toString(row[8]));
        dto.setStudentCode(toString(row[9]));
        return dto;
    }
    
    private UUID toUUID(Object obj) {
        if (obj == null) return null;
        if (obj instanceof UUID) return (UUID) obj;
        if (obj instanceof String s && !s.isEmpty()) {
            try { return UUID.fromString(s); } catch (Exception e) { return null; }
        }
        return null;
    }
    
    private String toString(Object obj) {
        if (obj == null) return null;
        return obj.toString();
    }
    
    private Boolean toBoolean(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Boolean) return (Boolean) obj;
        if (obj instanceof Number) return ((Number) obj).intValue() != 0;
        if (obj instanceof String s) return Boolean.parseBoolean(s);
        return null;
    }

    // Tìm kiếm đăng ký thi theo ID
    public Optional<ExamRegistration> findById(UUID id) {
        if (!examRegistrationRepository.findById(id).isPresent()) {
            throw new RuntimeException("Đăng ký thi với ID " + id + " không tồn tại.");
        }
        return examRegistrationRepository.findById(id);
    }

    // Lọc theo examRoomId - lấy danh sách thí sinh trong 1 phòng thi
    public List<ExamRegistration> findByExamRoomId(String examRoomId) {
        return examRegistrationRepository.findByExamRoomId(examRoomId);
    }

    // Lọc theo examId
    public List<ExamRegistration> findByExamId(String examId) {
        return examRegistrationRepository.findByExamId(examId);
    }

    // Lọc theo examId và examRoomId
    public List<ExamRegistration> findByExamIdAndExamRoomId(String examId, String examRoomId) {
        return examRegistrationRepository.findByExamIdAndExamRoomId(examId, examRoomId);
    }

    // Lọc theo studentId
    public List<ExamRegistration> findByStudentId(String studentId) {
        return examRegistrationRepository.findByStudentId(studentId);
    }

    // Lọc theo isActive
    public List<ExamRegistration> findByIsActive(Boolean isActive) {
        return examRegistrationRepository.findByIsActive(isActive);
    }

    // Lưu hoặc cập nhật đăng ký thi
    public ExamRegistration save(ExamRegistration examRegistration) {
        logger.info("Saving examRegistration: examId={}, examRoomId={}, studentId={}, rollNumber={}, isActive={}",
                examRegistration.getExamId(), examRegistration.getExamRoomId(),
                examRegistration.getStudentId(), examRegistration.getRollNumber(),
                examRegistration.getIsActive());
        
        if (examRegistration.getCreatedAt() == null) {
            examRegistration.setCreatedAt(LocalDateTime.now());
        }
        if (examRegistration.getIsActive() == null) {
            examRegistration.setIsActive(true);
        }
        examRegistration.setUpdatedAt(LocalDateTime.now());
        
        try {
            ExamRegistration saved = examRegistrationRepository.save(examRegistration);
            logger.info("Saved successfully with id={}", saved.getId());
            return saved;
        } catch (Exception e) {
            logger.error("Error saving examRegistration: {}", e.getMessage(), e);
            throw e;
        }
    }

    // Xóa đăng ký thi theo ID
    public void deleteById(UUID id) {
        if (!examRegistrationRepository.findById(id).isPresent()) {
            throw new RuntimeException("Đăng ký thi với ID " + id + " không tồn tại.");
        }
        examRegistrationRepository.deleteById(id);
    }
}
