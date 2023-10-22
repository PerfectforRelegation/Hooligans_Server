package com.example.hooligan01.repository;

import com.example.hooligan01.entity.Points;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PointRepository extends JpaRepository<Points, UUID> {
}
