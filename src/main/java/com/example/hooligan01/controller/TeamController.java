package com.example.hooligan01.controller;

import com.example.hooligan01.entity.Teams;
import com.example.hooligan01.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    // 팀 리스트
    @GetMapping("/teamList")
    public List<Teams> teamsList() {

        return teamService.teamList();
    }

    // 팀 데이터 넣기
    @PostMapping("/teamInsert")
    public Boolean teamInsert(@RequestBody Teams team) {

        return teamService.teamInsert(team);
    }


}