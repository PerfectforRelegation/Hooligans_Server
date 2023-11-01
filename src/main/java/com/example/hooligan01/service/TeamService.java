package com.example.hooligan01.service;

import com.example.hooligan01.dto.Message;
import com.example.hooligan01.entity.Teams;
import com.example.hooligan01.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    // 팀 리스트
    public List<Teams> teamList() {

        return teamRepository.findAll();
    }

    public Message teamSave(JSONObject jsonObject) {

        try {
            JSONArray leagueTable = (JSONArray) jsonObject.get("leaguetable");

            for (Object team : leagueTable) {

                JSONObject teamInfo = (JSONObject) team;

                Teams inputTeam = Teams.builder()
                        .teamId((String) teamInfo.get("teamid"))
                        .teamName((String) teamInfo.get("teamname"))
                        .teamLogo((String) teamInfo.get("team_logo"))
                        .played(Integer.parseInt((String) teamInfo.get("played")))
                        .won(Integer.parseInt((String) teamInfo.get("won")))
                        .drawn(Integer.parseInt((String) teamInfo.get("drawn")))
                        .lost(Integer.parseInt((String) teamInfo.get("lost")))
                        .gf(Integer.parseInt((String) teamInfo.get("gf")))
                        .ga(Integer.parseInt((String) teamInfo.get("ga")))
                        .gd(Integer.parseInt((String) teamInfo.get("gd")))
                        .points(Integer.parseInt((String) teamInfo.get("points")))
                        .build();

                teamRepository.save(inputTeam);
            }

            return Message.builder().message("팀 저장 완료").build();
        } catch (Exception e) {
            return Message.builder().message("팀 저장 안됨 " + e).build();
        }
        //        JSONArray leagueTable = (JSONArray) jsonObject.get("leaguetable");
//
//        for (Object team : leagueTable) {
//
//            JSONObject teamInfo = (JSONObject) team;
//
//            Teams inputTeam = Teams.builder()
//                    .teamId((String) teamInfo.get("teamid"))
//                    .teamName((String) teamInfo.get("teamname"))
//                    .teamLogo((String) teamInfo.get("team_logo"))
//                    .played(Integer.parseInt((String) teamInfo.get("played")))
//                    .won(Integer.parseInt((String) teamInfo.get("won")))
//                    .drawn(Integer.parseInt((String) teamInfo.get("drawn")))
//                    .lost(Integer.parseInt((String) teamInfo.get("lost")))
//                    .gf(Integer.parseInt((String) teamInfo.get("gf")))
//                    .ga(Integer.parseInt((String) teamInfo.get("ga")))
//                    .gd(Integer.parseInt((String) teamInfo.get("gd")))
//                    .points(Integer.parseInt((String) teamInfo.get("points")))
//                    .build();
//
//            teamRepository.save(inputTeam);
//        }
    }
}
