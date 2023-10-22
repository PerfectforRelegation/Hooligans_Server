package com.example.hooligan01.repository;

import com.example.hooligan01.entity.Boards;
import com.example.hooligan01.entity.Heart;
import com.example.hooligan01.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Boolean existsByHeartBoardsAndHeartUsers(Boards heartBoards, Users heartUsers);

    Heart findByHeartBoardsAndHeartUsers(Boards board, Users user);
}
