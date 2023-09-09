package com.example.hooligan01.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Bets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long betId;

    @Column(nullable = false)
    private String betUserNick;

    @Column(nullable = false)
    private String betMatch;

    @Column(nullable = false)
    private String betInfo;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int betCoin;

}
