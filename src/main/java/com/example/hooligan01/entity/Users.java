package com.example.hooligan01.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userNick;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userLogId;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String userPhone;

    @Column(nullable = false)
    private String userBirth;

    @Column
    private String userToken;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int userCoin;

    @Column
    private String userTeam;
}
