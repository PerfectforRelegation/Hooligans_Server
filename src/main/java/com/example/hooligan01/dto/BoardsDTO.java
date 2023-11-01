package com.example.hooligan01.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BoardsDTO {

    private Long id;
    private String nickname;
    private String title;
    private String content;
    private Integer commentCount;
    private Integer heartCount;
    private Integer view;
    private Boolean modified;
    private LocalDate boardDate;
}
