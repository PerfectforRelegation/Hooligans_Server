package com.example.hooligan01.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder @AllArgsConstructor @NoArgsConstructor

public class Users {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String account;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
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

    @OneToMany(mappedBy = "user")
    private List<Boards> boards = new ArrayList<>();

    public void addBoard(Boards board) {
        this.boards.add(board);
        board.setUser(this);
    }

    @OneToMany(mappedBy = "heartUsers")
    private List<Heart> hearts = new ArrayList<>();

    public void addHeart(Heart heart) {
        this.hearts.add(heart);
        heart.setHeartUsers(this);
    }

    @OneToMany(mappedBy = "users")
    private List<Points> points = new ArrayList<>();

    public void addPoint(Points point) {
        this.points.add(point);
        point.setUsers(this);
    }

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userInfo")
//    private Bets betInfo;

    // authority
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Authorities> roles = new ArrayList<>();

    public void setRoles(List<Authorities> role) {
        this.roles = role;
        role.forEach(o -> o.setUser(this));
    }
}
