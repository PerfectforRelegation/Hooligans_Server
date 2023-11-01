package com.example.hooligan01.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder @NoArgsConstructor @AllArgsConstructor
public class Teams {

    @Id
    private String teamId;

    @Column(nullable = false)
    private String teamName;

    @Column(nullable = false)
    private String teamLogo;

    @Column(nullable = false)
    private Integer played;

    @Column(nullable = false)
    private Integer won;

    @Column(nullable = false)
    private Integer drawn;

    @Column(nullable = false)
    private Integer lost;

    @Column(nullable = false)
    private Integer gf;

    @Column(nullable = false)
    private Integer ga;

    @Column(nullable = false)
    private Integer gd;

    @Column(nullable = false)
    private Integer points;
}
