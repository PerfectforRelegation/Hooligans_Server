package com.example.hooligan01.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class BetsDTO {

    private UUID id;
    private LocalDate date;
    private String home;
    private String away;
    private Double homeAllocation;
    private Double awayAllocation;
    private Double drawAllocation;
    private String win;
}
