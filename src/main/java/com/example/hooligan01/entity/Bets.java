package com.example.hooligan01.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Bets {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "bet_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fixture_id")
    private Fixtures fixtures;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int homePoint;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int awayPoint;

    @Column
    private String win;

    @Column(columnDefinition = "double default 0")
    private Double allocation;

    @OneToMany(mappedBy = "bet", cascade = CascadeType.ALL)
    private List<Users> users = new ArrayList<>();

    public void addUser(Users user) {
        this.users.add(user);
        user.setBet(this);
    }

    @OneToMany(mappedBy = "bets")
    private List<Points> points = new ArrayList<>();

    public void addPoint(Points point) {
        this.points.add(point);
        point.setBets(this);
    }
}
