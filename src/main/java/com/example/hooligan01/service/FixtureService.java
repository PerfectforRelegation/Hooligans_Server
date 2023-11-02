package com.example.hooligan01.service;

import com.example.hooligan01.dto.FixtureDTO;
import com.example.hooligan01.dto.Message;
import com.example.hooligan01.entity.Bets;
import com.example.hooligan01.entity.Fixtures;
import com.example.hooligan01.repository.BetRepository;
import com.example.hooligan01.repository.FixtureRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FixtureService {

    private final FixtureRepository fixtureRepository;
    private final BetRepository betRepository;

    // json 파일 받아서 해보는 것 테스트
    public ResponseEntity<Object> fixtureSave(JSONObject jsonObject) {

        try {
            String league = (String) jsonObject.get("league");

            JSONArray fixtures = (JSONArray) jsonObject.get("fixtures");

            for (Object fixture : fixtures) {
                JSONObject fixtureInfo = (JSONObject) fixture;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E d MMM yyyy");
                LocalDate date = LocalDate.parse((String) fixtureInfo.get("date"), formatter.withLocale(Locale.US));

                Long hs = (Long) fixtureInfo.get("homeScore");
                Long as = (Long) fixtureInfo.get("awayScore");

                Long homeAllocation = (Long) fixtureInfo.get("homeAllocation");
                Long awayAllocation = (Long) fixtureInfo.get("awayAllocation");
                Long drawAllocation = (Long) fixtureInfo.get("drawAllocation");

                Optional<Fixtures> findFixture =
                        fixtureRepository.findByHomeAndAwayAndDate((String) fixtureInfo.get("home"), (String) fixtureInfo.get("away"), date);

                if (findFixture.isPresent()) {

                    Fixtures updateFixture = findFixture.get();

                    updateFixture.setHomeScore(Math.toIntExact(hs));
                    updateFixture.setAwayScore(Math.toIntExact(as));
                    updateFixture.setTime((String) fixtureInfo.get("time"));
                    updateFixture.setStatus((String) fixtureInfo.get("status"));

                    fixtureRepository.save(updateFixture);

                    Optional<Bets> getBet = betRepository.findByFixturesId(updateFixture.getId());

                    if (getBet.isPresent()) {

                        Bets bet = getBet.get();

                        // status 값이 "POST"가 아니면 bet 의 win 값을 null
                        if (!updateFixture.getStatus().equals("POST")) {

                            bet.setWin(null);
                            betRepository.save(bet);
                        } else {

                            String win = (updateFixture.getHomeScore() > updateFixture.getAwayScore()) ?
                                    updateFixture.getHome() : (updateFixture.getHomeScore() < updateFixture.getAwayScore()) ?
                                    updateFixture.getAway() : "DRAW";
                            bet.setWin(win);

                            betRepository.save(bet);
                        }
                    }
                } else {

                    Fixtures inputFixture = Fixtures.builder()
                            .league(league)
                            .date(date)
                            .home((String) fixtureInfo.get("home"))
                            .away((String) fixtureInfo.get("away"))
                            .stadium((String) fixtureInfo.get("stadium"))
                            .homeScore(Math.toIntExact(hs))
                            .awayScore(Math.toIntExact(as))
                            .time((String) fixtureInfo.get("time"))
                            .homeAllocation(Double.valueOf(homeAllocation))
                            .awayAllocation(Double.valueOf(awayAllocation))
                            .drawAllocation(Double.valueOf(drawAllocation))
                            .status((String) fixtureInfo.get("status"))
                            .build();

                    fixtureRepository.save(inputFixture);
                    Bets bet = new Bets();
                    bet.setFixtures(inputFixture);
                    betRepository.save(bet);
                }
            }

            return new ResponseEntity<>(new Message("경기 저장"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("fixtureSave error " + e), HttpStatus.OK);
        }
    }

    public List<FixtureDTO> getAllList() {

        return fixtureRepository.findAllWithoutBets();
    }

    public List<Fixtures> getListToMain() {

        return fixtureRepository.findTop5ByOrderByDateDesc();
    }

    public List<Fixtures> getPreFixturesList() {

        return fixtureRepository.findAllByStatus("PRE");
    }

    public List<Fixtures> getLiveFixturesList() {

        return fixtureRepository.findAllByStatus("LIVE");
    }

    public List<Fixtures> getPostFixturesList() {

        return fixtureRepository.findAllByStatus("POST");
    }
}
