package com.example.hooligan01.controller;

import com.example.hooligan01.entity.Fixtures;
import com.example.hooligan01.service.FixtureService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
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

    @GetMapping("/test")
    public boolean leagueTable() throws Exception {
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("/C:/Users/jody8/OneDrive/바탕 화면/premier-league_fixtures.json");
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        fixtureService.testSave(jsonObject);

        return true;
    }

    @GetMapping("/list")
    public List<Fixtures> getAllList() {

        return fixtureService.getAllList();
    }
}
