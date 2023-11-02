package com.example.hooligan01.controller;

import com.example.hooligan01.dto.BoardsDTO;
import com.example.hooligan01.dto.BoardsDetailDTO;
import com.example.hooligan01.entity.Boards;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.security.UserDetailsImpl;
import com.example.hooligan01.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//3. main 에 뭐 넣지

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<Object> getAllBoards() {

        return boardService.findAllBoard();
    }

    // 게시글 등록 with 이미지
    @PostMapping("/write")
    public ResponseEntity<Object> boardEnroll(@RequestBody Boards board, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Users user = userDetails.getUser();

        board.setUser(user);

        return boardService.enroll(board);
    }

    // 게시글 등록
//    @PostMapping("/write")
//    public Boolean boardWrite(@RequestBody Boards board, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//        Users user = userDetails.getUser();
//
//        board.setUser(user);
//
//        boardService.write(board);
//
//        return true;
//    }

    // 게시글 상세보기(수정 및 삭제...?)  view 값 오름
    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> boardDetail(@PathVariable Long id) {

        return boardService.getBoardDetail(id);
    }

    // 게시글 수정(게시글 상세보기에서 수정 버튼을 누르는 식으로..?) -> update
    @GetMapping("/updateForm/{id}")
    public ResponseEntity<Object> boardUpdateForm(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return boardService.getBoardUpdateForm(id, userDetails);
    }

    // 게시글 수정(업데이트)
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Boards boards) {

        return boardService.update(boards);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteByBoardId(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return boardService.deleteByBoardId(id, userDetails);
    }
}
