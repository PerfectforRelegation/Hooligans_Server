package com.example.hooligan01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToMainDTO {

    private String nickname;
    private Integer betPoint;
    private String firstTeam;
    private String secondTeam;
    private String thirdTeam;
}
