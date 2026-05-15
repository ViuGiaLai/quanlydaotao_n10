package com.example.demo.n10.repository;

import com.example.demo.n10.model.entity.CourseClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseClassRepository extends JpaRepository<CourseClass, UUID> {
    List<CourseClass> findBySemesterId(UUID semesterId);
}