package com.example.hooligan01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private UUID id;
    private String nickname;
    private String comment;
}
