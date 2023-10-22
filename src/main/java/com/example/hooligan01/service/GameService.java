package com.example.hooligan01.service;

import com.example.hooligan01.entity.Bets;
import com.example.hooligan01.entity.Games;
import com.example.hooligan01.repository.BetRepository;
import com.example.hooligan01.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final BetRepository betRepository;

    // bet 이 같이 저장됨
    public Boolean save(Games game) {

        gameRepository.save(game);

        Bets bet = new Bets();
        bet.setGames(game);
        betRepository.save(bet);

        return true;
    }

    // json 파일 받아서 해보는 것 테스트
    public void testSave(JSONObject jsonObject) {

        String league = (String) jsonObject.get("league");

        JSONArray games = (JSONArray) jsonObject.get("games");
        for (Object game : games) {
            JSONObject gameDayJson = (JSONObject) game;

            String gameDay = (String) gameDayJson.get("date");
            JSONArray gameInfos = (JSONArray) gameDayJson.get("game");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd MMM yyyy");
            LocalDate date = LocalDate.parse(gameDay, formatter.withLocale(Locale.US));

            // List<Games> inputGameList = new ArrayList<>();

            for (Object gameInfo : gameInfos) {
                JSONObject gameInfoJson = (JSONObject) gameInfo;

                Games inputGame = Games.builder()
                        .league(league)
                        .homeTeam((String) gameInfoJson.get("home"))
                        .awayTeam((String) gameInfoJson.get("away"))
                        .date(date)
                        .time((String) gameInfoJson.get("time"))
                        .build();

//                String time = (String) gameInfoJson.get("time");
//                String home = (String) gameInfoJson.get("home");
//                String away = (String) gameInfoJson.get("away");

                //inputGameList.add(inputGame);

                gameRepository.save(inputGame);
                Bets bet = new Bets();
                bet.setGames(inputGame);
                betRepository.save(bet);
            }
        }
    }

    public List<Games> getAllList() {

        return gameRepository.findAll();
    }
}
