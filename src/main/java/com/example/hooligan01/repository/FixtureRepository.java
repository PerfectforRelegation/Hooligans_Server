package com.example.hooligan01.repository;

import com.example.hooligan01.entity.Fixtures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FixtureRepository extends JpaRepository<Fixtures, UUID> {
}
