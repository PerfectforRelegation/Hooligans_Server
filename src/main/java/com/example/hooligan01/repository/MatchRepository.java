package com.example.hooligan01.repository;

import com.example.hooligan01.entity.Matches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Matches, Long> {
}
