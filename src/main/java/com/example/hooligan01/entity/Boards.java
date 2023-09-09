package com.example.hooligan01.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Boards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false)
    private String boardUserNick;

    @Column(nullable = false)
    private String boardTitle;

    @Column(nullable = false)
    private String boardContent;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int boardLike;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int boardUnlike;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int boardView;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate boardDate;
}
