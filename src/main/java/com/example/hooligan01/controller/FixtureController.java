package com.example.hooligan01.controller;

import com.example.hooligan01.entity.Fixtures;
import com.example.hooligan01.service.FixtureService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fixture")
public class FixtureController {

    private final FixtureService fixtureService;

    @PostMapping
    public Boolean input(@RequestBody Fixtures fixture) {

        return fixtureService.save(fixture);
    }

//    @GetMapping("/table")
//    public boolean leagueTable() throws Exception {
//        JSONParser parser = new JSONParser();//"/home/ubuntu/crawling_python/fixtures.json"
//        Reader reader = new FileReader("C:/Users/jody8/OneDrive/바탕 화면/fixtures.json");
//        JSONObject jsonObject = (JSONObject) parser.parse(reader);
//
//        fixtureService.fixtureSave(jsonObject);
//
//        return true;
//    }

    // cron = "0 0 6 * * *" -> 매일 아침 6시마다 / "0 */2 * * * *" 2분마다
    @Scheduled(cron = "0 0 6 * * *", zone = "Asia/Seoul")
    public void loadFixtureTable() throws IOException, ParseException {
        JSONParser parser = new JSONParser();// "C:/Users/jody8/OneDrive/바탕 화면/fixtures.json"
        Reader reader = new FileReader("/home/ubuntu/crawling_python/fixtures.json");
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        fixtureService.fixtureSave(jsonObject);
    }

    @GetMapping("/list")
    public List<Fixtures> getAllList() {

        return fixtureService.getAllList();
    }

    @GetMapping("/list/pre")
    public List<Fixtures> getPreFixturesList() {

        return fixtureService.getPreFixturesList();
    }

    @GetMapping("/list/live")
    public List<Fixtures> getLiveFixturesList() {

        return fixtureService.getLiveFixturesList();
    }

    @GetMapping("/list/post")
    public List<Fixtures> getPostFixturesList() {

        return fixtureService.getPostFixturesList();
    }
}
