package com.example.hooligan01.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Teams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String league;
}
