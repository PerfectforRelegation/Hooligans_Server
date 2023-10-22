package com.example.hooligan01.controller;

import com.example.hooligan01.entity.Games;
import com.example.hooligan01.service.GameService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @PostMapping
    public Boolean input(@RequestBody Games game) {

        return gameService.save(game);
    }

    @GetMapping("/test")
    public boolean leagueTable() throws Exception {
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("/home/ubuntu/crawling_python/fixtures.json");
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        gameService.testSave(jsonObject);

        return true;
    }

    @GetMapping("/list")
    public List<Games> getAllList() {

        return gameService.getAllList();
    }
}
