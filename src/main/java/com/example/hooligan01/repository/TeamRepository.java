package com.example.hooligan01.repository;

import com.example.hooligan01.entity.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Teams, Long> {
    Optional<Teams> findByLeague(String teamLeague);
}
