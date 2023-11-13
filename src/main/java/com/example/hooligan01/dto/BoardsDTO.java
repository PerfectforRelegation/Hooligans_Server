package com.example.hooligan01.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

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
    private LocalDateTime boardDate;
    private String filename;
    private String filepath;
}
