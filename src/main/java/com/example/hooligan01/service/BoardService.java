package com.example.hooligan01.service;

import com.example.hooligan01.dto.BoardsDTO;
import com.example.hooligan01.entity.Boards;
import com.example.hooligan01.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    // 모든 게시글 리스트 출력
//    public List<Boards> boardList() {
//
//        return boardRepository.findAll();
//    }

    // 게시글 출력
    public List<BoardsDTO> findAllWithNickName() {

        return boardRepository.findAllWithNickname();
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

}
