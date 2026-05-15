package com.example.demo.n2.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.example.demo.n2.model.entity.Building;
import com.example.demo.n2.repository.BuildingRepository;

@Service
public class BuildingService {
    private final BuildingRepository repository;

    public BuildingService(BuildingRepository repository) {
        this.repository = repository;
    }

    public List<Building> findAll() {
        return repository.findAll();
    }

    public Building save(Building obj) {
        if (obj.getId() == null) {
            obj.setCreatedAt(LocalDateTime.now());
        }
        obj.setUpdatedAt(LocalDateTime.now());
        return repository.save(obj);
    }

    public java.util.Optional<Building> findById(UUID id) {
        return repository.findById(id);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}