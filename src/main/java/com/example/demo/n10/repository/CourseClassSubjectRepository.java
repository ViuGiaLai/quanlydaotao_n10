package com.example.demo.n10.repository;

import com.example.demo.n10.model.entity.CourseClassSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseClassSubjectRepository extends JpaRepository<CourseClassSubject, UUID> {
    List<CourseClassSubject> findByCourseClassId(UUID courseClassId);
    List<CourseClassSubject> findBySubjectId(UUID subjectId);
}