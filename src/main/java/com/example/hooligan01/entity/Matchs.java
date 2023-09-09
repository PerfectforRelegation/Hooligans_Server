package com.example.hooligan01.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Matchs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @Column(nullable = false)
    private String matchHomeTeam;

    @Column(nullable = false)
    private String matchAwayTeam;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int matchHomeScore;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int matchAwayScore;

    @Column(nullable = false)
    private String matchLocation;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate matchDate;
}
