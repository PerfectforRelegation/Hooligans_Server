package com.example.hooligan01.controller;

import com.example.hooligan01.dto.BoardsDTO;
import com.example.hooligan01.dto.BoardsDetailDTO;
import com.example.hooligan01.entity.Boards;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.security.UserDetailsImpl;
import com.example.hooligan01.service.BoardService;
import com.example.hooligan01.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//1. board/list -> 댓글 개수 추가
//2. board/detail -> hearts 대신 댓글 리턴, 댓글 보이기
//3. main 에 뭐 넣지
//4. heartService findAll.size 수정

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public List<BoardsDTO> getAllBoards() {

        return boardService.findAllBoard();
    }

    // 게시글 등록
    @PostMapping("/write")
    public Boolean boardWrite(@RequestBody Boards board, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Users user = userDetails.getUser();

        board.setUser(user);

        boardService.write(board);

        return true;
    }

    // 게시글 상세보기(수정 및 삭제...?)  view 값 오름
    @GetMapping("/detail/{id}")
    public ResponseEntity<BoardsDetailDTO> boardDetail(@PathVariable Long id) {

        return boardService.getBoardDetail(id);
    }

    // 게시글 수정(게시글 상세보기에서 수정 버튼을 누르는 식으로..?)
    @GetMapping("/updateForm/{id}")
    public Boards boardUpdateForm(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Users user = userDetails.getUser();

        Boards boards = boardService.findByBoardId(id);

        if (boards.getUser().getNickname().equals(user.getNickname())) {
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

//        Users user = userService.findByNickname((String) session.getAttribute("nickname"));
//
//        boards.setUser(user);

        return boardService.update(boards);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public Boolean deleteByBoardId(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Boards boards = boardService.findByBoardId(id);

        String myNickname = userDetails.getUser().getNickname();

        if (boards.getUser().getNickname().equals(myNickname)) {
            boardService.deleteByBoardId(id);
            return true;
        } else {
            return false;
        }
    }
}
