package com.example.hooligan01.controller;

import com.example.hooligan01.entity.Boards;
import com.example.hooligan01.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public List<Boards> boardList() {

        return boardService.boardList();
    }

    // 게시글 등록
    @PostMapping("/write")
    public Boolean boardWrite(@RequestBody Boards board, HttpSession session) {

        board.setBoardUserNick((String) session.getAttribute("userNick"));

        boardService.write(board);

        return true;
    }

    // 게시글 상세보기
    @GetMapping("/detail/{boardId}")
    public Boards boardDetail(@PathVariable Long boardId) {

        Boards board = boardService.findByBoardId(boardId);

        board.setBoardView(board.getBoardView() + 1);

        boardService.write(board);

        return board;
    }
}
