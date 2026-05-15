package com.example.demo.n10.dto;

import java.util.UUID;

public class ExamRegistrationDTO {
    private UUID id;
    private UUID examId;
    private UUID examRoomId;
    private UUID studentId;
    private String rollNumber;
    private Boolean isActive;
    
    // Thông tin join
    private String examName;
    private String roomName;
    private String studentName;
    private String studentCode;
    
    public ExamRegistrationDTO() {}
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public UUID getExamId() { return examId; }
    public void setExamId(UUID examId) { this.examId = examId; }
    
    public UUID getExamRoomId() { return examRoomId; }
    public void setExamRoomId(UUID examRoomId) { this.examRoomId = examRoomId; }
    
    public UUID getStudentId() { return studentId; }
    public void setStudentId(UUID studentId) { this.studentId = studentId; }
    
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public String getExamName() { return examName; }
    public void setExamName(String examName) { this.examName = examName; }
    
    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }
}