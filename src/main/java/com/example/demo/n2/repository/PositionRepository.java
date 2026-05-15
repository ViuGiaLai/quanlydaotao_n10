package com.example.demo.n2.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.n2.model.entity.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, UUID> {
}