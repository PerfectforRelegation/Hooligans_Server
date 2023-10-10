package com.example.hooligan01.entity;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@RequiredArgsConstructor
public class Users {
    public static Users EMPTY = Users.builder()
            .id(0L)
            .name("")
            .account("")
            .password("")
            .nickname("")
            .phoneNumber("")
            .birth("")
            .betPoint(0)
            .build();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
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
}
