package com.example.demo.n10.dto;

/**
 * Request DTO for admin special edit of exam results
 * Used for editing individual score components (attendance, test, midterm, final)
 */
public record AdminEditRequest(
    Double attendanceScore,
    Double testScore,
    Double midtermScore,
    Double finalScore,
    String adminNote
) {
}
