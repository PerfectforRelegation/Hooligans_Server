package com.example.hooligan01.controller;

import com.example.hooligan01.dto.BetsDTO;
import com.example.hooligan01.entity.Bets;
import com.example.hooligan01.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/list")
    public List<BetsDTO> getAllBets() {

        return betService.findList();
    }
}
