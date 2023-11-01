package com.example.hooligan01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainDTO {

    private UserToMainDTO user;
    private List<FixtureDTO> fixtures;
    private NewsDTO news;
}
