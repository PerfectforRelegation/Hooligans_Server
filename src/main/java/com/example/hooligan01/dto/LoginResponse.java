package com.example.hooligan01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private UUID id;
    private String name;
    private String account;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String birth;
    private int betPoint;
    private String firstTeam;
    private String secondTeam;
    private String thirdTeam;

    private TokenDto tokenDto;
}
