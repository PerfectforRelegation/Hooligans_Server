package com.example.hooligan01.controller;

import com.example.hooligan01.dto.TokenDto;
import com.example.hooligan01.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/token")
public class TokenController {

    private final JwtUtil jwtUtil;

    @GetMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(HttpServletRequest request) {

        TokenDto tokenDto = new TokenDto(jwtUtil.getHeaderToken(request, "Access"), jwtUtil.getHeaderToken(request, "Refresh"));

        return ResponseEntity.ok(tokenDto);
    }

}
