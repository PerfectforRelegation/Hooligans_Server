package com.example.hooligan01.repository;

import com.example.hooligan01.dto.FixtureDTO;
import com.example.hooligan01.entity.Fixtures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FixtureRepository extends JpaRepository<Fixtures, UUID> {

    List<Fixtures> findTop5ByOrderByDateDesc();

    Optional<Fixtures> findByHomeAndAwayAndDate(String home, String away, LocalDate date);

    List<Fixtures> findAllByStatus(String status);

    @Query("SELECT new com.example.hooligan01.dto.FixtureDTO(b.id, b.league, b.date, b.home, b.away, " +
            "b.stadium, b.homeScore, b.awayScore, b.time, b.homeAllocation, b.awayAllocation, b.drawAllocation, b.status) FROM Fixtures b")
    List<FixtureDTO> findAllWithoutBets();
}
