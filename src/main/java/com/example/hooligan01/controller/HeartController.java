package com.example.hooligan01.controller;

import com.example.hooligan01.entity.Boards;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.security.UserDetailsImpl;
import com.example.hooligan01.service.BoardService;
import com.example.hooligan01.service.HeartService;
import com.example.hooligan01.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/heart")
public class HeartController {

    private final HeartService heartService;
    private final BoardService boardService;

    @PostMapping("/{id}")
    public Boolean check(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Boards board = boardService.findByBoardId(id);
        Users user = userDetails.getUser();

        return heartService.checkHeart(board, user);

    }
}
