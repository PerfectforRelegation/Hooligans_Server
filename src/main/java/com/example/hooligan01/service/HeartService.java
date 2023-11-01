package com.example.hooligan01.service;

import com.example.hooligan01.dto.Message;
import com.example.hooligan01.entity.Boards;
import com.example.hooligan01.entity.Heart;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.repository.BoardRepository;
import com.example.hooligan01.repository.HeartRepository;
import com.example.hooligan01.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final BoardRepository boardRepository;

    public ResponseEntity<Object> checkHeart(Long id, UserDetailsImpl userDetails) {

        try {

            Optional<Boards> getBoard = boardRepository.findById(id);
            if (getBoard.isEmpty())
                return new ResponseEntity<>(new Message("checkHeart error : 아이디 값에 따른 게시판 없음"), HttpStatus.OK);

            Boards board = getBoard.get();
            Users user = userDetails.getUser();

            boolean exist = heartRepository.existsByHeartBoardsAndHeartUsers(board, user);

            // 게시판 좋아요 수 관리
            if (!exist) {

                Heart heart = new Heart();
                heart.setHeartBoards(board);
                heart.setHeartUsers(user);

                heartRepository.save(heart);

                board.setHeartCount(board.getHeartCount() + 1);
                boardRepository.save(board);

                return new ResponseEntity<>(new Message("좋아요 반영 성공"), HttpStatus.OK);
            } else {

                Heart heart = heartRepository.findByHeartBoardsAndHeartUsers(board, user);

                heartRepository.deleteById(heart.getId());

                board.setHeartCount(board.getHeartCount() - 1);
                boardRepository.save(board);

                return new ResponseEntity<>(new Message("좋아요 삭제 성공"), HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(new Message("checkHeart error " + e), HttpStatus.OK);
        }
    }
}
