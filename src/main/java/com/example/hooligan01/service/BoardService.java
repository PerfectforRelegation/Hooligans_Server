package com.example.hooligan01.service;

import com.example.hooligan01.dto.BoardsDTO;
import com.example.hooligan01.dto.BoardsDetailDTO;
import com.example.hooligan01.dto.CommentDTO;
import com.example.hooligan01.dto.Message;
import com.example.hooligan01.entity.BoardComments;
import com.example.hooligan01.entity.Boards;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.repository.BoardRepository;
import com.example.hooligan01.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 모든 게시글 리스트 출력
    public ResponseEntity<Object> findAllBoard() {

        try {
            List<BoardsDTO> boardsDTOS = boardRepository.findAllWithNicknameAndCount();

            if (boardsDTOS.isEmpty())
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
            else
                return new ResponseEntity<>(boardsDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new Message("findAllBoard error : " + e), HttpStatus.OK);
        }
    }
//    public ResponseEntity<Object> findAllBoard() {
//
//        return new ResponseEntity<>(boardRepository.findAll(), HttpStatus.OK);
//    }

    // 게시글 작성
//    public void write(Boards board) {
//
//        boardRepository.save(board);
//    }

    // 게시글 등록
    public ResponseEntity<Object> enroll(Boards board) {

        Message message;

        try {

            boardRepository.save(board);

            return new ResponseEntity<>(board, HttpStatus.OK);
        } catch (Exception e) {

            message = new Message("게시글 등록 에러 " + e);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    // 게시글을 보드아이디를 통해 찾음
    public Boards findByBoardId(Long id) {

        return boardRepository.findById(id).get();
    }

    // 게시글 업데이트
    public ResponseEntity<Object> update(Boards boards) {

        try {
            Optional<Boards> boardGet = boardRepository.findById(boards.getId());

            if (boardGet.isEmpty())
                return new ResponseEntity<>(new Message("BoardService.update 에러, 아이디 값에 따른 게시판 데이터 없음"), HttpStatus.OK);

            Boards board = boardGet.get();

            if (!board.isModified())
                board.setModified(true);

            if (boards.getTitle() != null)
                board.setTitle(boards.getTitle());

            if (boards.getContent() != null)
                board.setContent(boards.getContent());

            if (boards.getFilename() != null)
                board.setFilename(boards.getFilename());

            if (boards.getFilepath() != null)
                board.setFilepath(boards.getFilepath());

            boardRepository.save(board);

            return new ResponseEntity<>(new Message("게시판 수정 완료"), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new Message("게시판 업데이트 에러 " + e), HttpStatus.OK);
        }
    }

    // 게시글 삭제
    public ResponseEntity<Object> deleteByBoardId(Long id, UserDetailsImpl userDetails) {

        try {
            Optional<Boards> getBoard = boardRepository.findById(id);
            if (getBoard.isEmpty())
                return new ResponseEntity<>(new Message("deleteByBoardId 에러, 아이디 값에 따른 게시판 없음"), HttpStatus.OK);

            Boards board = getBoard.get();
            Users user = userDetails.getUser();

            if (board.getUser().getNickname().equals(user.getNickname())) {
                boardRepository.deleteById(id);
                return new ResponseEntity<>(new Message("게시글 삭제 완료"), HttpStatus.OK);
            } else
                return new ResponseEntity<>(new Message("deleteByBoardId 에러, 닉네임 불일치"), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new Message("deleteByBoardId 에러 " + e), HttpStatus.OK);
        }
    }

    // 게시글 상세
    public ResponseEntity<Object> getBoardDetail(Long id) {

        Message message;

        try {
            Optional<Boards> boardGet = boardRepository.findById(id);

            Boards board = boardGet.get();
            board.setView(board.getView() + 1);
            boardRepository.save(board);

            List<CommentDTO> commentDetails = new ArrayList<>();
            for (BoardComments comment : board.getComments()) {
                commentDetails.add(CommentDTO.builder()
                        .id(comment.getId())
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
                    .filename(board.getFilename())
                    .filepath(board.getFilepath())
                    .comments(commentDetails)
                    .build();

            return new ResponseEntity<>(boardsDetail, HttpStatus.OK);
        } catch (Exception e) {

            message = new Message("getBoardDetail 에러 " + e);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    // 게시판 수정 업데이트 폼
    public ResponseEntity<Object> getBoardUpdateForm(Long id, UserDetailsImpl userDetails) {

        Message message;

        try {

            Optional<Boards> getBoard = boardRepository.findById(id);
            if (getBoard.isEmpty()) {
                message = new Message("게시판 수정 폼 에러, 게시판 없음");
                return new ResponseEntity<>(message, HttpStatus.OK);
            }

            Boards board = getBoard.get();
            Users user = userDetails.getUser();

            if (board.getUser().getNickname().equals(user.getNickname()))
                return new ResponseEntity<>(board, HttpStatus.OK);
            else
                return new ResponseEntity<>(new Message("해당 게시판의 유저 데이터와 접속한 유저의 데이터 불일치"), HttpStatus.OK);

        } catch (Exception e) {

            message = new Message("게시판 수정 폼 에러 " + e);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
