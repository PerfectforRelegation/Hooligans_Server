package com.example.hooligan01.dto;

import com.example.hooligan01.entity.BoardComments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private LocalDate boardDate;
    private List<CommentDTO> comments;
}
