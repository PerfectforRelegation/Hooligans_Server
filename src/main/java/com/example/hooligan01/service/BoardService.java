package com.example.hooligan01.service;

import com.example.hooligan01.entity.Boards;
import com.example.hooligan01.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    public List<Boards> boardList() {

        return boardRepository.findAll();
    }

    public void write(Boards board) {

        boardRepository.save(board);
    }

    public Boards findByBoardId(Long boardId) {

        return boardRepository.findByBoardId(boardId).get();
    }
}
