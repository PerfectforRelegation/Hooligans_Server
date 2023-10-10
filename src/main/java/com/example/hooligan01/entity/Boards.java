package com.example.hooligan01.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Boards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

//    @Column(nullable = false)
//    private String nickname;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int heartCount;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private boolean modified;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate boardDate;

    @OneToMany(mappedBy = "heartBoards")
    private List<Heart> hearts;

    public void addHeart(Heart heart) {
        this.hearts.add(heart);
        heart.setHeartBoards(this);
    }
}
