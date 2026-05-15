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
        dto.setId((UUID) row[0]);
        dto.setRegistrationId((UUID) row[1]);
        dto.setCourseClassId((UUID) row[2]);
        dto.setSubjectId((UUID) row[3]);
        dto.setScore((Double) row[4]);
        dto.setAttendanceScore((Double) row[5]);
        dto.setTestScore((Double) row[6]);
        dto.setMidtermScore((Double) row[7]);
        dto.setFinalScore((Double) row[8]);
        dto.setTotalScore((Double) row[9]);
        dto.setStatus((String) row[10]);
        dto.setGradedBy((LocalDateTime) row[11]);
        dto.setGradedAt((LocalDateTime) row[12]);
        dto.setIsLocked((Boolean) row[13]);
        dto.setEditStatus((String) row[14]);
        dto.setScoreType((String) row[15]);
        dto.setCreatedAt((LocalDateTime) row[16]);
        dto.setUpdatedAt((LocalDateTime) row[17]);
        dto.setIsActive((Boolean) row[22]);
        // Student info từ join (index 24, 25)
        dto.setStudentCode((String) row[24]);
        dto.setStudentName((String) row[25]);
        return dto;
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
