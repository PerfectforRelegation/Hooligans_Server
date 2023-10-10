package com.example.hooligan01.controller;

import com.example.hooligan01.dto.BoardsDTO;
import com.example.hooligan01.entity.Boards;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.service.BoardService;
import com.example.hooligan01.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

//    @GetMapping("/list")
//    public List<Boards> boardList() {
//
//        return boardService.boardList();
//    }

    @GetMapping("/list")
    public List<BoardsDTO> getAllBoards() {

        return boardService.findAllWithNickName();
    }

    // 게시글 등록
    @PostMapping("/write")
    public Boolean boardWrite(@RequestBody Boards board, HttpSession session) {

        // board.setNickname((String) session.getAttribute("nickname"));

        Users user = userService.findByNickname((String) session.getAttribute("nickname"));

        board.setUser(user);

        boardService.write(board);

        return true;
    }

    // 게시글 상세보기(수정 및 삭제...?)  view 값 오름
    // 세션 값으로 비교 후 수정, 삭제 버튼이 생길 수 있도록 boolean 반환 값을 주어야 할까..?
    @GetMapping("/detail/{id}")
    public Boards boardDetail(@PathVariable Long id) {

        Boards board = boardService.findByBoardId(id);

        board.setView(board.getView() + 1);

        boardService.write(board);

        return board;
    }

    // 게시글 수정(게시글 상세보기에서 수정 버튼을 누르는 식으로..?)
    @GetMapping("/updateForm/{id}")
    public Boards boardUpdateForm(@PathVariable Long id, HttpSession session) {

        String myNickname = (String) session.getAttribute("nickname");

        Boards boards = boardService.findByBoardId(id);

        if (boards.getUser().getNickname().equals(myNickname)) {
            return boards;
        } else {
            return null;
        }
    }

    // 게시글 수정(업데이트)
    @PutMapping("/update")
    public Boolean update(@RequestBody Boards boards) {

        if (!boards.isModified())
            boards.setModified(true);

        return boardService.update(boards);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public Boolean deleteByBoardId(@PathVariable Long id, HttpSession session) {

        Boards boards = boardService.findByBoardId(id);

        String myNickname = (String) session.getAttribute("nickname");

        if (boards.getUser().getNickname().equals(myNickname)) {
            boardService.deleteByBoardId(id);
            return true;
        } else {
            return false;
        }
    }
}
