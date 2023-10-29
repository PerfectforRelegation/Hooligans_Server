package com.example.hooligan01.repository;

import com.example.hooligan01.entity.Points;
import com.example.hooligan01.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PointRepository extends JpaRepository<Points, UUID> {

    Optional<Points> findPointsByBetsIdAndUsers(UUID id, Users users);
}
