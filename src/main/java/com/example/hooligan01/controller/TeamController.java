package com.example.hooligan01.controller;

import com.example.hooligan01.entity.Teams;
import com.example.hooligan01.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    // 팀 리스트
    @GetMapping("/list")
    public List<Teams> teamsList() {

        return teamService.teamList();
    }

//    @GetMapping("/table")
//    public Message leagueTable() throws Exception {
//        JSONParser parser = new JSONParser();
//        Reader reader = new FileReader("/home/ubuntu/crawling_python/premier-league.json");
//        JSONObject jsonObject = (JSONObject) parser.parse(reader);
//
//        return teamService.teamSave(jsonObject);
//    }

    // cron = "0 0 6 * * *" -> 매일 아침 6시마다 / "0 */2 * * * *" 2분마다
    @Scheduled(cron = "0 */5 * * * *", zone = "Asia/Seoul")
    public void loadLeagueTable() throws IOException, ParseException {
        JSONParser parser = new JSONParser();           //"C:/Users/jody8/OneDrive/바탕 화면/premier-league.json"
        Reader reader = new FileReader("/home/ubuntu/crawling_python/premier-league.json");
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        teamService.teamSave(jsonObject);
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