package com.example.hooligan01.repository;

import com.example.hooligan01.dto.UserBetPointDTO;
import com.example.hooligan01.entity.Bets;
import com.example.hooligan01.entity.Betting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BettingRepository extends JpaRepository<Betting, UUID> {

    List<Betting> findAllByUsersId(UUID id);
}
