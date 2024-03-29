package com.example.hooligan01.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
public class Points {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "point_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bet_id")
    private Bets bets;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int betPoint;

    @Column(nullable = false)
    private String pick;

    // true 면 수취된 것
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private boolean result;
}
