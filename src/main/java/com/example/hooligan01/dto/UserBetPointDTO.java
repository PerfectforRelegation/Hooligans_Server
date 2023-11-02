package com.example.hooligan01.dto;

import com.example.hooligan01.entity.Points;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBetPointDTO {

    private UUID id;
    private LocalDate date;
    private String home;
    private String away;
    private Double homeAllocation;
    private Double awayAllocation;
    private Double drawAllocation;
    private String win;
    private Points point;
}
