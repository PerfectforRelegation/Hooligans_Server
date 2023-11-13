package com.example.hooligan01.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Boards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int commentCount;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int heartCount;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private boolean modified;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime boardDate;

    /** 새로 추가한 코드 **/
    @Column
    private String filename;

    @Column
    private String filepath;
    /** ㅡㅡㅡㅡㅡㅡㅡㅡ **/

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "heartBoards", cascade = CascadeType.ALL)
    private List<Heart> hearts = new ArrayList<>();

    public void addHeart(Heart heart) {
        this.hearts.add(heart);
        heart.setHeartBoards(this);
    }

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<BoardComments> comments = new ArrayList<>();

    public void addComment(BoardComments comment) {
        this.comments.add(comment);
        comment.setBoard(this);
    }
}
