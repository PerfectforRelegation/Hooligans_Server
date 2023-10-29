package com.example.hooligan01.service;

import com.example.hooligan01.entity.Boards;
import com.example.hooligan01.entity.Heart;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.repository.BoardRepository;
import com.example.hooligan01.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final BoardRepository boardRepository;

    public Boolean checkHeart(Boards board, Users user) {

        boolean exist = heartRepository.existsByHeartBoardsAndHeartUsers(board, user);

        // 게시판 좋아요 수 관리
        if (!exist) {

            Heart heart = new Heart();
            heart.setHeartBoards(board);
            heart.setHeartUsers(user);

            heartRepository.save(heart);

            board.setHeartCount(board.getHeartCount() + 1);
            boardRepository.save(board);

            return true;
        } else {

            Heart heart = heartRepository.findByHeartBoardsAndHeartUsers(board, user);

            heartRepository.deleteById(heart.getId());

            board.setHeartCount(board.getHeartCount() - 1);
            boardRepository.save(board);

            return false;
        }
    }
}
