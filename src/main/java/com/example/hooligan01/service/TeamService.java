package com.example.hooligan01.service;

import com.example.hooligan01.entity.Teams;
import com.example.hooligan01.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    // 팀 리스트
    public List<Teams> teamList() {

        return teamRepository.findAll();
    }

    // 팀 데이터 넣기
    public Boolean teamInsert(Teams team) {

        Optional<Teams> byLeague = teamRepository.findByLeague(team.getLeague());

        if (byLeague.isEmpty()) {
            teamRepository.save(team);
            return true;
        } else
            return false;
    }
}
