package com.example.demo.n10.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.n10.dto.ExamResultDTO;
import com.example.demo.n10.model.entity.ExamResult;
import com.example.demo.n10.model.constants.ScoreStatus;
import com.example.demo.n10.repository.ExamResultRepository;

@Service
public class ExamResultService {

    private final ExamResultRepository examResultRepository;

    public ExamResultService(ExamResultRepository examResultRepository) {
        this.examResultRepository = examResultRepository;
    }

    // Tìm kiếm tất cả kết quả thi
    public List<ExamResult> findAll() {
        return examResultRepository.findAll();
    }

    // Tìm kiếm tất cả kết quả thi với thông tin student (DTO)
    public List<ExamResultDTO> findAllWithStudent() {
        List<Object[]> rows = examResultRepository.findAllWithStudent();
        return rows.stream().map(this::mapToDTO).toList();
    }
    
    // Tìm kiếm theo courseClassId với thông tin student (DTO)
    public List<ExamResultDTO> findByCourseClassIdWithStudent(UUID courseClassId) {
        List<Object[]> rows = examResultRepository.findByCourseClassIdWithStudent(courseClassId);
        return rows.stream().map(this::mapToDTO).toList();
    }
    
    // Map Object[] sang ExamResultDTO
    private ExamResultDTO mapToDTO(Object[] row) {
        ExamResultDTO dto = new ExamResultDTO();
        dto.setId(toUUID(row[0]));
        dto.setRegistrationId(toUUID(row[1]));
        dto.setCourseClassId(toUUID(row[2]));
        dto.setSubjectId(toUUID(row[3]));
        dto.setScore(toDouble(row[4]));
        dto.setAttendanceScore(toDouble(row[5]));
        dto.setTestScore(toDouble(row[6]));
        dto.setMidtermScore(toDouble(row[7]));
        dto.setFinalScore(toDouble(row[8]));
        dto.setTotalScore(toDouble(row[9]));
        dto.setStatus(toString(row[10]));
        dto.setGradedBy(toLocalDateTime(row[11]));
        dto.setGradedAt(toLocalDateTime(row[12]));
        dto.setIsLocked(toBoolean(row[13]));
        dto.setEditStatus(toString(row[14]));
        dto.setScoreType(toString(row[15]));
        dto.setCreatedAt(toLocalDateTime(row[16]));
        dto.setUpdatedAt(toLocalDateTime(row[17]));
        dto.setIsActive(toBoolean(row[22]));
        // Student info từ join (index 25, 26, 27, 28)
        dto.setStudentCode(toString(row[25]));
        dto.setStudentName(toString(row[26]));
        dto.setSubjectName(toString(row[27]));
        dto.setClassName(toString(row[28]));
        return dto;
    }
    
    // Safe type conversion helpers
    private UUID toUUID(Object obj) {
        if (obj == null) return null;
        if (obj instanceof UUID) return (UUID) obj;
        if (obj instanceof String s && !s.isEmpty()) {
            try { return UUID.fromString(s); } catch (Exception e) { return null; }
        }
        return null;
    }
    
    private Double toDouble(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Double) return (Double) obj;
        if (obj instanceof Number) return ((Number) obj).doubleValue();
        if (obj instanceof String s) {
            try { return Double.parseDouble(s); } catch (Exception e) { return null; }
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
    
    private LocalDateTime toLocalDateTime(Object obj) {
        if (obj == null) return null;
        if (obj instanceof LocalDateTime) return (LocalDateTime) obj;
        return null;
    }

    // Tìm kiếm kết quả thi theo ID
    public Optional<ExamResult> findById(UUID id) {
        return examResultRepository.findById(id);
    }

    // Lưu hoặc cập nhật kết quả thi
    public ExamResult save(ExamResult examResult) {
        if (examResult.getCreatedAt() == null) {
            examResult.setCreatedAt(LocalDateTime.now());
        }
        if (examResult.getIsActive() == null) {
            examResult.setIsActive(true);
        }
        // Set default editStatus if null - mặc định là OPEN để GV nhập điểm được
        if (examResult.getEditStatus() == null) {
            examResult.setEditStatus("OPEN");
        }
        examResult.setUpdatedAt(LocalDateTime.now());
        return examResultRepository.save(examResult);
    }

    // Xóa kết quả thi theo ID
    public void deleteById(UUID id) {
        if (!examResultRepository.findById(id).isPresent()) {
            throw new RuntimeException("Kết quả thi với ID " + id + " kh��ng tồn tại.");
        }
        examResultRepository.deleteById(id);
    }

    // Tìm kiếm theo bộ lọc
    public List<ExamResult> findByFilters(UUID courseClassId, UUID subjectId, String status, Boolean isEditable, String search) {
        List<ExamResult> results = examResultRepository.findAll();
        
        // Lọc theo courseClassId
        if (courseClassId != null) {
            results = results.stream()
                .filter(r -> courseClassId.equals(r.getCourseClassId()))
                .toList();
        }
        
        // Lọc theo subjectId (optional)
        if (subjectId != null && !subjectId.toString().isEmpty()) {
            results = results.stream()
                .filter(r -> subjectId.equals(r.getSubjectId()))
                .toList();
        }
        
        // Lọc theo status (optional) - DRAFT, SUBMITTED, APPROVED
        if (status != null && !status.isEmpty()) {
            results = results.stream()
                .filter(r -> status.equals(r.getStatus()))
                .toList();
        }
        
        // Lọc theo isEditable (optional) - kiểm tra editStatus
        if (isEditable != null) {
            results = results.stream()
                .filter(r -> {
                    String editStatus = r.getEditStatus();
                    // Nếu editStatus NULL, coi là OPEN (mở nhập)
                    if (editStatus == null || "OPEN".equals(editStatus)) {
                        return isEditable;
                    }
                    // editStatus = "LOCKED"
                    return !isEditable;
                })
                .toList();
        }
        
        return results;
    }
    
    // ========== Score Status Methods ==========
    
    /**
     * Gửi điểm (DRAFT → SUBMITTED)
     * Giảng viên bấm "Gửi điểm"
     */
    public ExamResult submitScore(UUID resultId) {
        ExamResult result = findById(resultId)
                .orElseThrow(() -> new RuntimeException("Kết quả thi không tồn tại"));
        
        if (!ScoreStatus.canSubmit(result.getStatus())) {
            throw new RuntimeException("Không thể gửi điểm ở trạng thái: " + result.getStatus());
        }
        
        result.setStatus(ScoreStatus.SUBMITTED);
        result.setGradedAt(LocalDateTime.now());
        return save(result);
    }
    
    /**
     * Gửi nhiều điểm cùng lúc
     */
    public List<ExamResult> submitScores(List<UUID> resultIds) {
        return resultIds.stream()
                .map(this::submitScore)
                .toList();
    }
    
    /**
     * Duyệt điểm (SUBMITTED → APPROVED)
     * Admin bấm "Duyệt"
     */
    public ExamResult approveScore(UUID resultId) {
        ExamResult result = findById(resultId)
                .orElseThrow(() -> new RuntimeException("Kết quả thi không tồn tại"));
        
        if (!ScoreStatus.canApprove(result.getStatus())) {
            throw new RuntimeException("Không thể duyệt điểm ở trạng thái: " + result.getStatus());
        }
        
        result.setStatus(ScoreStatus.APPROVED);
        result.setGradedAt(LocalDateTime.now());
        return save(result);
    }
    
    /**
     * Duyệt nhiều điểm cùng lúc
     */
    public List<ExamResult> approveScores(List<UUID> resultIds) {
        return resultIds.stream()
                .map(this::approveScore)
                .toList();
    }
    
    /**
     * Trả lại điểm (SUBMITTED → REJECTED → DRAFT)
     * Admin bấm "Trả lại" - cho phép GV sửa lại
     */
    public ExamResult returnScore(UUID resultId) {
        ExamResult result = findById(resultId)
                .orElseThrow(() -> new RuntimeException("Kết quả thi không tồn tại"));
        
        if (!ScoreStatus.canReturn(result.getStatus())) {
            throw new RuntimeException("Không thể trả lại điểm ở trạng thái: " + result.getStatus());
        }
        
        result.setStatus(ScoreStatus.DRAFT);
        result.setGradedAt(LocalDateTime.now());
        return save(result);
    }
    
    /**
     * Trả lại nhiều điểm cùng lúc
     */
    public List<ExamResult> returnScores(List<UUID> resultIds) {
        return resultIds.stream()
                .map(this::returnScore)
                .toList();
    }
    
    /**
     * Khóa điểm (APPROVED → LOCKED)
     * Admin bấm "Khóa" - không cho sửa nữa
     */
    public ExamResult lockScore(UUID resultId) {
        ExamResult result = findById(resultId)
                .orElseThrow(() -> new RuntimeException("Kết quả thi không tồn tại"));
        
        if (!ScoreStatus.isApproved(result.getStatus())) {
            throw new RuntimeException("Chỉ có thể khóa điểm đã duyệt");
        }
        
        result.setIsLocked(true);
        result.setGradedAt(LocalDateTime.now());
        return save(result);
    }
    
    /**
     * Kiểm tra xem sinh viên có thể xem điểm không
     */
    public boolean canStudentView(UUID resultId) {
        return findById(resultId)
                .map(r -> ScoreStatus.isApproved(r.getStatus()))
                .orElse(false);
    }
    
    /**
     * Kiểm tra xem giảng viên có thể sửa điểm không
     */
    public boolean canTeacherEdit(UUID resultId) {
        return findById(resultId)
                .map(r -> ScoreStatus.canEdit(r.getStatus()))
                .orElse(false);
    }
}
