package com.example.demo.n10.model.constants;

/**
 * Trạng thái điểm trong hệ thống
 * 
 * Workflow: DRAFT → SUBMITTED → APPROVED → PUBLISHED → LOCKED
 * 
 * DRAFT - Giảng viên lưu tạm, chưa gửi, có thể sửa được
 * SUBMITTED - Giảng viên đã gửi, chờ Admin duyệt, GV không sửa được
 * APPROVED - Admin đã duyệt, chuẩn bị công bố
 * PUBLISHED - Admin công bố cho sinh viên xem, không cho sửa
 * LOCKED - Lưu trữ, không sửa, không xóa
 * REJECTED - Admin từ chối, trả lại cho GV nhập lại
 * 
 * Trạng thái cho phép nhập điểm (OPEN/LOCKED):
 * OPEN - Admin mở cho phép GV nhập điểm
 * LOCKED - Admin khóa, GV không nhập được
 */
public final class ScoreStatus {
    
    public static final String DRAFT = "DRAFT";
    public static final String SUBMITTED = "SUBMITTED";
    public static final String APPROVED = "APPROVED";
    public static final String PUBLISHED = "PUBLISHED";
    public static final String LOCKED_STATUS = "LOCKED";
    public static final String REJECTED = "REJECTED";
    
    // Trạng thái cho phép nhập điểm
    public static final String OPEN = "OPEN";
    public static final String LOCKED = "LOCKED";
    
    private ScoreStatus() {
        // Utility class
    }
    
    public static boolean canEdit(String status) {
        return DRAFT.equals(status);
    }
    
    public static boolean canSubmit(String status) {
        return DRAFT.equals(status);
    }
    
    public static boolean canApprove(String status) {
        return SUBMITTED.equals(status);
    }
    
    public static boolean canReturn(String status) {
        return SUBMITTED.equals(status) || APPROVED.equals(status);
    }
    
    public static boolean canPublish(String status) {
        return APPROVED.equals(status);
    }
    
    public static boolean canView(String status) {
        return APPROVED.equals(status) || PUBLISHED.equals(status);
    }
    
    public static boolean isApproved(String status) {
        return APPROVED.equals(status);
    }

    public static boolean isPublished(String status) {
        return PUBLISHED.equals(status);
    }
    
    public static boolean canBeModified(String status) {
        // Only DRAFT and SUBMITTED can be modified
        return DRAFT.equals(status) || SUBMITTED.equals(status);
    }
    
    // Kiểm tra trạng thái cho phép nhập điểm
    public static boolean isOpen(String editStatus) {
        return OPEN.equals(editStatus);
    }

    public static boolean isLocked(String editStatus) {
        return LOCKED.equals(editStatus);
    }
}