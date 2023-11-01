package com.example.hooligan01.service;

import com.example.hooligan01.dto.Message;
import com.example.hooligan01.entity.BoardComments;
import com.example.hooligan01.entity.Boards;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.repository.BoardCommentRepository;
import com.example.hooligan01.repository.BoardRepository;
import com.example.hooligan01.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardCommentService {

    private final BoardRepository boardRepository;
    private final BoardCommentRepository commentRepository;

    // 댓글 등록
    public ResponseEntity<Object> commentSave(Long id, BoardComments comment, UserDetailsImpl userDetails) {

        Message message;

        try {

            Optional<Boards> board = boardRepository.findById(id);

            if (board.isEmpty()) {
                message = new Message("게시판 없음");
                return new ResponseEntity<>(message, HttpStatus.OK);
            }

            Users user = userDetails.getUser();

            if (user == null) {
                message = new Message("유저 없음");
                return new ResponseEntity<>(message, HttpStatus.OK);
            }

            comment.setBoard(board.get());
            comment.setUser(user);

            commentRepository.save(comment);

            // 게시판 댓글 증가
            Boards boardUpdate = board.get();
            boardUpdate.setCommentCount(boardUpdate.getCommentCount() + 1);
            boardRepository.save(boardUpdate);

            message = new Message("댓글 등록 완료");
            return new ResponseEntity<>(message, HttpStatus.OK);

        } catch (Exception e) {

            message = new Message(String.valueOf(e));
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> commentDelete(UUID id, UserDetailsImpl userDetails) {

        Message message;

        try {

            Optional<BoardComments> comment = commentRepository.findById(id);

            if (comment.isEmpty()) {
                message = new Message("댓글 없음");
                return new ResponseEntity<>(message, HttpStatus.OK);
            }

            Users user = userDetails.getUser();

            if (user == null) {
                message = new Message("유저 없음");
                return new ResponseEntity<>(message, HttpStatus.OK);
            }

            if (!comment.get().getUser().getAccount().equals(user.getAccount())) {
                message = new Message("현재 로그인 한 아이디와 댓글의 아이디 불일치");
                return new ResponseEntity<>(message, HttpStatus.OK);
            }

            commentRepository.deleteById(id);

            Optional<Boards> board = boardRepository.findById(comment.get().getBoard().getId());
            Boards boardUpdate = board.get();
            boardUpdate.setCommentCount(boardUpdate.getCommentCount() - 1);
            boardRepository.save(boardUpdate);

            message = new Message("댓글 삭제 완료");
            return new ResponseEntity<>(message, HttpStatus.OK);

        } catch (Exception e) {

            message = new Message(String.valueOf(e));
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
