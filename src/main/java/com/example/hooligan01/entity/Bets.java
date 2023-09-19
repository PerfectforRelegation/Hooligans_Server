package com.example.hooligan01.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Bets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String match;

    @Column(nullable = false)
    private String info;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int betPoint;

}
