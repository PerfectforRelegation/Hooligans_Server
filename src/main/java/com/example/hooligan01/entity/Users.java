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

    @Column(nullable = false)
    private String favoriteTeam;

//    @Column
//    private String secondTeam;
//
//    @Column
//    private String thirdTeam;

    @Column
    private int betPoint;

    /** 새로 추가한 코드 **/
    @Column
    private String filename;

    @Column
    private String filepath;
    /** ㅡㅡㅡㅡㅡㅡㅡㅡ **/

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Boards> boards = new ArrayList<>();

    public void addBoard(Boards board) {
        this.boards.add(board);
        board.setUser(this);
    }

    @OneToMany(mappedBy = "heartUsers", cascade = CascadeType.ALL)
    private List<Heart> hearts = new ArrayList<>();

    public void addHeart(Heart heart) {
        this.hearts.add(heart);
        heart.setHeartUsers(this);
    }

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Betting> betting = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Points> points = new ArrayList<>();

    public void addPoint(Points point) {
        this.points.add(point);
        point.setUsers(this);
    }

    @OneToMany(mappedBy = "user")
    private List<BoardComments> comments = new ArrayList<>();

    public void addComment(BoardComments comment) {
        this.comments.add(comment);
        comment.setUser(this);
    }

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private RefreshToken token;
}
