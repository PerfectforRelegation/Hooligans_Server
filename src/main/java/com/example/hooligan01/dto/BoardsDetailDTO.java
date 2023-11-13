package com.example.hooligan01.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardsDetailDTO {

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
    private List<CommentDTO> comments;
}
