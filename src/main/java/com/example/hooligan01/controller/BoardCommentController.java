package com.example.hooligan01.controller;

import com.example.hooligan01.entity.BoardComments;
import com.example.hooligan01.security.UserDetailsImpl;
import com.example.hooligan01.service.BoardCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class BoardCommentController {

    private final BoardCommentService commentService;

    // 경로에 게시판 아이디 값
    @PostMapping("/up/{id}")
    public ResponseEntity<Object> commentUp(
            @PathVariable Long id, @RequestBody BoardComments comment,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentService.commentSave(id, comment, userDetails);
    }

    // 경로에 댓글 아이디 값
    @DeleteMapping("/down/{id}")
    public ResponseEntity<Object> commentDown(
            @PathVariable UUID id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentService.commentDelete(id, userDetails);
    }
}
