package com.example.hooligan01.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(nullable = false)
    private Integer homeScore;

    @Column(nullable = false)
    private Integer awayScore;

    @Column
    private String time;

    /** 새롭게 추가된 것! **/
    @Column
    private Double homeAllocation;

    @Column
    private Double awayAllocation;

    @Column
    private Double drawAllocation; // 무승부

    @Column // PRE, LIVE, POST -> 시작 전, 경기 중, 경기 끝
    private String status;
    /** 새롭게 추가된 것! **/

    @OneToOne(mappedBy = "fixtures", cascade = CascadeType.ALL)
    private Bets bets;
}
