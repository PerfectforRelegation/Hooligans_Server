package com.example.hooligan01.controller;

import com.example.hooligan01.entity.Teams;
import com.example.hooligan01.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.io.Reader;
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

    @GetMapping("/table")
    public JSONObject leagueTable() throws Exception {
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("/home/ubuntu/crawling_python/premier-league.json");
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        
        return jsonObject;
    }

    /*@GetMapping(
            value = "/get-image-with-media-type",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaType() throws IOException {
        InputStream in = getClass().getResourceAsStream("/com/baeldung/produceimage/image.jpg");
        return IOUtils.toByteArray(in);
    }*/


}