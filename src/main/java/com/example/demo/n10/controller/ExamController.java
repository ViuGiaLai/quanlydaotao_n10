package com.example.demo.n10.controller;

import com.example.demo.n10.model.entity.Exam;
import com.example.demo.n10.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController // QUAN TRỌNG: Phải là RestController để trả về JSON
@RequestMapping("/api/exams") // Khớp hoàn toàn với đường dẫn bạn gọi
@CrossOrigin("*") // Chống lỗi chặn truy cập từ trình duyệt
public class ExamController {

    @Autowired
    private ExamService examService;

    // Lấy danh sách 20 lịch thi bạn vừa thêm trong SQL
    @GetMapping
    public List<Exam> listExams() {
        return examService.getAllExams();
    }

    @GetMapping("/list")
    public List<Exam> listAllExams() {
        return examService.getAllExams();
    }

    @PostMapping
    public Exam saveExam(@RequestBody Exam exam) {
        return examService.saveExam(exam);
    }

    @DeleteMapping("/{id}")
    public void deleteExam(@PathVariable UUID id) {
        examService.deleteExam(id); // Gọi hàm xóa từ service
    }

    // Thêm hàm xử lý cập nhật (PUT)
    @PutMapping("/{id}")
    public Exam updateExam(@PathVariable UUID id, @RequestBody Exam exam) {
        exam.setId(id); // Đảm bảo ID được giữ nguyên để cập nhật đúng dòng
        return examService.saveExam(exam); // Tận dụng hàm save có sẵn
    }
}