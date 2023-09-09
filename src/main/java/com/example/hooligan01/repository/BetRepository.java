package com.example.hooligan01.repository;

import com.example.hooligan01.entity.Bets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetRepository extends JpaRepository<Bets, Long> {
}
