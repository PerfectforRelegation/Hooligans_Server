package com.example.hooligan01.service;

import com.example.hooligan01.dto.BoardsDTO;
import com.example.hooligan01.dto.BoardsDetailDTO;
import com.example.hooligan01.dto.CommentDTO;
import com.example.hooligan01.entity.BoardComments;
import com.example.hooligan01.entity.Boards;
import com.example.hooligan01.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 모든 게시글 리스트 출력
    public List<BoardsDTO> findAllBoard() {

        return boardRepository.findAllWithNicknameAndCount();
    }

    // 게시글 작성
    public void write(Boards board) {

        boardRepository.save(board);
    }

    // 게시글을 보드아이디를 통해 찾음
    public Boards findByBoardId(Long id) {

        return boardRepository.findById(id).get();
    }

    // 게시글 업데이트
    public Boolean update(Boards boards) {

        boardRepository.save(boards);

        return true;
    }

    // 게시글 삭제
    public void deleteByBoardId(Long id) {
        boardRepository.deleteById(id);
    }

    public ResponseEntity<BoardsDetailDTO> getBoardDetail(Long id) {

        Optional<Boards> boardGet = boardRepository.findById(id);

        Boards board = boardGet.get();
        board.setView(board.getView() + 1);
        boardRepository.save(board);

        List<CommentDTO> commentDetails = new ArrayList<>();
        for (BoardComments comment : board.getComments()) {
            commentDetails.add(CommentDTO.builder()
                    .nickname(comment.getUser().getNickname())
                    .comment(comment.getComment())
                    .build());
        }

        BoardsDetailDTO boardsDetail = BoardsDetailDTO.builder()
                .id(board.getId())
                .nickname(board.getUser().getNickname())
                .title(board.getTitle())
                .content(board.getContent())
                .commentCount(board.getCommentCount())
                .heartCount(board.getHeartCount())
                .view(board.getView())
                .modified(board.isModified())
                .boardDate(board.getBoardDate())
                .comments(commentDetails)
                .build();

        return new ResponseEntity<>(boardsDetail, HttpStatus.OK);
    }
}
