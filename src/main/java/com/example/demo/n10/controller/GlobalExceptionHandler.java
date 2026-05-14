package com.example.demo.n10.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        logger.error("Exception: {} - {}", e.getClass().getName(), e.getMessage(), e);
        
        Map<String, Object> response = new HashMap<>();
        response.put("error", e.getClass().getSimpleName());
        response.put("message", e.getMessage());
        response.put("cause", e.getCause() != null ? e.getCause().getMessage() : null);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        logger.error("Data integrity violation: {}", e.getMessage());
        
        String message = e.getMessage();
        String userMessage = "Lỗi dữ liệu";
        
        // Check for specific duplicate key errors
        if (message != null && message.contains("duplicate key")) {
            if (message.contains("user_roles")) {
                userMessage = "User này đã được gán vai trò này rồi!";
            } else if (message.contains("UQ_")) {
                userMessage = "Dữ liệu đã tồn tại, không thể thêm mới!";
            } else {
                userMessage = "Trùng lặp dữ liệu!";
            }
        } else if (message != null && message.contains("foreign key")) {
            userMessage = "Dữ liệu tham chiếu không tồn tại!";
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("error", "DataIntegrityViolation");
        response.put("message", userMessage);
        response.put("details", e.getMessage());
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        logger.error("Type mismatch: {}", e.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("error", "TypeMismatch");
        response.put("message", e.getMessage());
        response.put("parameter", e.getName());
        response.put("requiredType", e.getRequiredType() != null ? e.getRequiredType().getName() : null);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(org.springframework.web.bind.MissingRequestValueException.class)
    public ResponseEntity<Map<String, Object>> handleMissingValue(org.springframework.web.bind.MissingRequestValueException e) {
        logger.error("Missing value: {}", e.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("error", "MissingValue");
        response.put("message", "Thiếu dữ liệu: " + e.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}