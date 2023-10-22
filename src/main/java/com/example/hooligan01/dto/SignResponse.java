package com.example.hooligan01.dto;

import com.example.hooligan01.entity.Authorities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignResponse {

    private UUID id;

    private String name;

    private String account;

    private String password;

    private String nickname;

    private String phoneNumber;

    private String birth;

    private int betPoint;

    private List<Authorities> roles = new ArrayList<>();

    private String token;
}
