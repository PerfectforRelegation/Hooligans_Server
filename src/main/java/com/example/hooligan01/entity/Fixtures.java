package com.example.hooligan01.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Fixtures {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "fixture_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String league;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String home;

    @Column(nullable = false)
    private String away;

    @Column(nullable = false)
    private String stadium;

    @Column
    private boolean isLive;

    @Column(nullable = false)
    private int homeScore;

    @Column(nullable = false)
    private int awayScore;

    @Column
    private String time;

    @OneToOne(mappedBy = "fixtures", cascade = CascadeType.ALL)
    private Bets bets;

    @Builder
    public Fixtures(String league, LocalDate date, String home, String away, String stadium, boolean isLive, int homeScore, int awayScore, String time) {
        this.league = league;
        this.date = date;
        this.home = home;
        this.away = away;
        this.stadium = stadium;
        this.isLive = isLive;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.time = time;
    }
}
