package com.example.hooligan01.controller;

import com.example.hooligan01.security.UserDetailsImpl;
import com.example.hooligan01.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/heart")
public class HeartController {

    private final HeartService heartService;

    // 경로에 board 아이디 값
    @PostMapping("/{id}")
    public ResponseEntity<Object> check(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return heartService.checkHeart(id, userDetails);
    }
}
