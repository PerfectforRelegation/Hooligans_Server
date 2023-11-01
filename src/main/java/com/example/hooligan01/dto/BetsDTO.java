package com.example.hooligan01.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class BetsDTO {

    private UUID id;
    private String home;
    private String away;
    private String win;
}
