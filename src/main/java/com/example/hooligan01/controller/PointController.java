package com.example.hooligan01.controller;

import com.example.hooligan01.entity.Points;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.security.UserDetailsImpl;
import com.example.hooligan01.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

    private final PointService pointService;

    @PostMapping("/bet/{id}")
    public ResponseEntity<Object> pointBetting(@PathVariable UUID id, @RequestBody Points point, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Users user = userDetails.getUser();

        return pointService.saveBetting(id, point, user);
    }


    // 보상받기
    // true or false 로 버튼 활성화
//    @PutMapping("/reward/{id}")
//    public ResponseEntity<Objects> betResult(@PathVariable UUID id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//        Users user = userDetails.getUser();
//
//        return pointService.getReword(id, user);
//    }
}
