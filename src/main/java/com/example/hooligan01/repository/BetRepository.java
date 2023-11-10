package com.example.hooligan01.repository;

import com.example.hooligan01.dto.BetsDTO;
import com.example.hooligan01.dto.UserBetPointDTO;
import com.example.hooligan01.entity.Bets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BetRepository extends JpaRepository<Bets, UUID> {

    Optional<Bets> findByFixturesId(UUID id);

    List<Bets> findAllById(UUID id);

    @Query("SELECT new com.example.hooligan01.dto.BetsDTO(b.id, u.date, u.home, u.away," +
            " u.homeAllocation, u.awayAllocation, u.drawAllocation, b.win) " +
            "FROM Bets b JOIN b.fixtures u")
    List<BetsDTO> findAllWithTeam();
}
