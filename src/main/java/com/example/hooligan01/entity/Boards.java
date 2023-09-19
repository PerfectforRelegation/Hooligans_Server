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
    private Long id;

    @Column(nullable = false)
    private String nick;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int heart;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int hate;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    @Column
    private String modified;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate boardDate;
}
