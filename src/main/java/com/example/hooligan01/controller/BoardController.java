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

        board.setNick((String) session.getAttribute("nick"));

        boardService.write(board);

        return true;
    }

    // 게시글 상세보기(수정 및 삭제...?)
    @GetMapping("/detail/{id}")
    public Boards boardDetail(@PathVariable Long id) {

        Boards board = boardService.findByBoardId(id);

        board.setView(board.getView() + 1);

        boardService.write(board);

        return board;
    }

    // 게시글 수정(게시글 상세보기에서 수정 버튼을 누르는 식으로..?)
    @GetMapping("updateForm/{id}")
    public Boards boardUpdateForm(@PathVariable Long id, HttpSession session) {

        String myNick = (String) session.getAttribute("nick");

        Boards boards = boardService.findByBoardId(id);

        if (boards.getNick().equals(myNick)) {
            return boards;
        } else {
            return null;
        }
    }

    // 게시글 수정(업데이트)
    @PostMapping("update")
    public Boolean update(@RequestBody Boards boards) {

        if (boards.getModified() == null)
            boards.setModified("수정됨");

        return boardService.update(boards);
    }

    // 게시글 삭제
    @GetMapping("/delete/{id}")
    public Boolean deleteByBoardId(@PathVariable Long id, HttpSession session) {

        Boards boards = boardService.findByBoardId(id);

        String myNick = (String) session.getAttribute("nick");

        if (boards.getNick().equals(myNick)) {
            boardService.deleteByBoardId(id);
            return true;
        } else {
            return false;
        }
    }
}
