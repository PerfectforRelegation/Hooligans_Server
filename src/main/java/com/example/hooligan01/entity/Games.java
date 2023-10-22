package com.example.hooligan01.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Games {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "game_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String league;

    @Column(nullable = false)
    private String homeTeam;

    @Column(nullable = false)
    private String awayTeam;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int homeScore;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int awayScore;

//    @Column(nullable = false)
//    private String location;

    // @CreationTimestamp
    @Column(nullable = false)
    private LocalDate date;

    @Column
    private String time;

    @OneToOne(mappedBy = "games", cascade = CascadeType.ALL)
    private Bets bets;

    @Builder
    public Games(String league, String homeTeam, String awayTeam, LocalDate date, String time) {
        this.league = league;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.date = date;
        this.time = time;
    }
}
