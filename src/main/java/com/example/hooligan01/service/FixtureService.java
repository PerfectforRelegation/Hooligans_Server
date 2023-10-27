package com.example.hooligan01.service;

import com.example.hooligan01.entity.Bets;
import com.example.hooligan01.entity.Fixtures;
import com.example.hooligan01.repository.BetRepository;
import com.example.hooligan01.repository.FixtureRepository;
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
public class FixtureService {

    private final FixtureRepository fixtureRepository;
    private final BetRepository betRepository;

    // bet 이 같이 저장됨
    public Boolean save(Fixtures fixture) {

        fixtureRepository.save(fixture);

        Bets bet = new Bets();
        bet.setFixtures(fixture);
        betRepository.save(bet);

        return true;
    }

    // json 파일 받아서 해보는 것 테스트
    public void testSave(JSONObject jsonObject) {

        String league = (String) jsonObject.get("league");

        JSONArray fixtures = (JSONArray) jsonObject.get("fixtures");
        for (Object fixture : fixtures) {
            JSONObject fixtureDayJson = (JSONObject) fixture;

            String fixtureDay = (String) fixtureDayJson.get("date");
            JSONArray fixtureInfos = (JSONArray) fixtureDayJson.get("fixture");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd MMM yyyy");
            LocalDate date = LocalDate.parse(fixtureDay, formatter.withLocale(Locale.US));

            // List<Games> inputGameList = new ArrayList<>();

            for (Object fixtureInfo : fixtureInfos) {
                JSONObject fixtureInfoJson = (JSONObject) fixtureInfo;

                Fixtures inputFixture = Fixtures.builder()
                        .league(league)
                        .homeTeam((String) fixtureInfoJson.get("home"))
                        .awayTeam((String) fixtureInfoJson.get("away"))
                        .date(date)
                        .time((String) fixtureInfoJson.get("time"))
                        .build();

//                String time = (String) gameInfoJson.get("time");
//                String home = (String) gameInfoJson.get("home");
//                String away = (String) gameInfoJson.get("away");

                //inputGameList.add(inputGame);

                fixtureRepository.save(inputFixture);
                Bets bet = new Bets();
                bet.setFixtures(inputFixture);
                betRepository.save(bet);
            }
        }
    }

    public List<Fixtures> getAllList() {

        return fixtureRepository.findAll();
    }
}
