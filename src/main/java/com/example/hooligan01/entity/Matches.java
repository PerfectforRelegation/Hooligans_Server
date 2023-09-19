package com.example.hooligan01.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Matches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String homeTeam;

    @Column(nullable = false)
    private String awayTeam;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int homeScore;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int awayScore;

    @Column(nullable = false)
    private String location;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate matchDate;
}
