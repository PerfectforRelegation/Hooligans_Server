package com.example.hooligan01.controller;

import com.example.hooligan01.dto.BetsDTO;
import com.example.hooligan01.entity.Bets;
import com.example.hooligan01.entity.Boards;
import com.example.hooligan01.security.UserDetailsImpl;
import com.example.hooligan01.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bet")
public class BetController {

    private final BetService betService;

    // 한 경기 배팅의 정보 리스트
    @GetMapping("/list/{id}")
    public Bets getOne(@PathVariable UUID id) {

        return betService.getById(id);
    }

    // 모든 경기 배팅 리스트
    @GetMapping("/list")
    public List<BetsDTO> getAllBetList() {

        return betService.findList();
    }

    // 버튼 누를 수 있는 api, bet 아이디 값
    @GetMapping("/reward/{id}")
    public Boolean canGetReward(@PathVariable UUID id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return betService.canGetReward(id, userDetails);
    }

    // 수취 api, bet 아이디 값
    @PostMapping("/reward")
    public ResponseEntity<Object> getReward(@RequestBody Bets bets, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return betService.getReward(bets.getId(), userDetails);
    }
}
