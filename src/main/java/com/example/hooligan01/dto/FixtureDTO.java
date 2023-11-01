package com.example.hooligan01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class FixtureDTO {

    private UUID id;
    private String league;
    private LocalDate date;
    private String home;
    private String away;
    private String stadium;
    private Integer homeScore;
    private Integer awayScore;
    private String time;
    private Double homeAllocation;
    private Double awayAllocation;
    private Double drawAllocation;
    private String status;
}
