package com.example.hooligan01.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String birth;

//    @Column
//    private String accessToken;

    @Column
    private String firstTeam;

    @Column
    private String secondTeam;

    @Column
    private String thirdTeam;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int betPoint;
}
