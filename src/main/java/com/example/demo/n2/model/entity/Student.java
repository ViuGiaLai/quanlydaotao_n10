package com.example.demo.n2.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue
    @UuidGenerator   // Hibernate 6+
    @Column(columnDefinition = "UNIQUEIDENTIFIER",
            updatable = false,
            nullable = false)
    private UUID id;

    @Column(name = "user_id", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID user_id;
    
    @Column(name = "code", length = 100, unique = true)
    private String code;
	
    @Column(name = "full_name", length = 255)
    private String fullname;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "gender", length = 10)
    private String gender;  // 1: Nam, 2: Nữ, 0: Khác

    @Column(name = "personal_identification_number", length = 20)
    private String personalIdentificationNumber;

    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @Column(name = "card_place", length = 100)
    private String cardPlace;

    @Column(name = "address", length = 300)
    private String address;

    @Column(name = "current_address", length = 300)
    private String currentAddress;

    @Column(name = "academic_year_year", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID academicYearYear;
    
    @Column(name = "department_id", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID departmentId;

    @Column(name = "major_id", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID majorId;
    
    @Column(name = "training_program_id", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID trainingProgramId;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "student_classe_id", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID studentClasseId;

    @Column(name = "admission_year")
    private LocalDateTime admissionYear;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID createdBy;

    @Column(name = "updated_by", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by", columnDefinition = "UNIQUEIDENTIFIER")
    private UUID deletedBy;

    @Column(name = "is_active")
    private Boolean isActive;
    

    public Student() {}

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUser_id() { return user_id; }
    public void setUser_id(UUID user_id) { this.user_id = user_id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPersonalIdentificationNumber() { return personalIdentificationNumber; }
    public void setPersonalIdentificationNumber(String personalIdentificationNumber) { this.personalIdentificationNumber = personalIdentificationNumber; }

    public LocalDate getDateOfIssue() { return dateOfIssue; }
    public void setDateOfIssue(LocalDate dateOfIssue) { this.dateOfIssue = dateOfIssue; }

    public String getCardPlace() { return cardPlace; }
    public void setCardPlace(String cardPlace) { this.cardPlace = cardPlace; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCurrentAddress() { return currentAddress; }
    public void setCurrentAddress(String currentAddress) { this.currentAddress = currentAddress; }

    public UUID getAcademicYearYear() { return academicYearYear; }
    public void setAcademicYearYear(UUID academicYearYear) { this.academicYearYear = academicYearYear; }

    public UUID getDepartmentId() { return departmentId; }
    public void setDepartmentId(UUID departmentId) { this.departmentId = departmentId; }

    public UUID getMajorId() { return majorId; }
    public void setMajorId(UUID majorId) { this.majorId = majorId; }

    public UUID getTrainingProgramId() { return trainingProgramId; }
    public void setTrainingProgramId(UUID trainingProgramId) { this.trainingProgramId = trainingProgramId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public UUID getStudentClasseId() { return studentClasseId; }
    public void setStudentClasseId(UUID studentClasseId) { this.studentClasseId = studentClasseId; }

    public LocalDateTime getAdmissionYear() { return admissionYear; }
    public void setAdmissionYear(LocalDateTime admissionYear) { this.admissionYear = admissionYear; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }

    public UUID getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(UUID updatedBy) { this.updatedBy = updatedBy; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }

    public UUID getDeletedBy() { return deletedBy; }
    public void setDeletedBy(UUID deletedBy) { this.deletedBy = deletedBy; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}