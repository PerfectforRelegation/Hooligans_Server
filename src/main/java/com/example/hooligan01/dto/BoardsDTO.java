package com.example.hooligan01.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BoardsDTO {

    private Long id;
    private String nickname;
    private String title;
    private String content;
    private int heartCount;
    private int view;
    private boolean modified;
    private LocalDate boardDate;
}
