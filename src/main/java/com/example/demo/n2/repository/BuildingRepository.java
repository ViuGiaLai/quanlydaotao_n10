package com.example.demo.n2.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.n2.model.entity.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, UUID> {
}