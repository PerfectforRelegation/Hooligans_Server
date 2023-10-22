package com.example.hooligan01.controller;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.Reader;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController {

    @GetMapping("/table")
    public JSONObject leagueTable() throws Exception {
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("/home/ubuntu/crawling_python/fixtures.json");
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        return jsonObject;
    }
}
