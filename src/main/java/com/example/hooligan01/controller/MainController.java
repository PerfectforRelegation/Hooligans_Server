package com.example.hooligan01.controller;

import com.example.hooligan01.dto.*;
import com.example.hooligan01.entity.Fixtures;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.security.UserDetailsImpl;
import com.example.hooligan01.service.FixtureService;
import com.example.hooligan01.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final UserService userService;
    private final FixtureService fixtureService;

    // 뉴스, 경기 최신 약 5개, 프로필 및 게시판 사진
    @GetMapping
    public ResponseEntity<Object> getMain(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            // 뉴스 리스트 불러오기 //"/home/ubuntu/crawling_python/news.json"
            JSONParser parser = new JSONParser();//
            Reader reader = new InputStreamReader(new FileInputStream("C:/Users/jody8/OneDrive/바탕 화면/news.json"), "euc-kr");
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
            JSONArray posts = (JSONArray) jsonObject.get("posts");

            NewsDTO news = new NewsDTO();
            news.setPlatform((String) jsonObject.get("platform"));
            news.setUrl((String) jsonObject.get("url"));

            for (Object post : posts) {

                JSONObject postInfo = (JSONObject) post;

                String titleValue = (String) postInfo.get("title");

                NewsDTO.Posts getPost = NewsDTO.Posts.builder()
                        .title(titleValue)
                        .href((String) postInfo.get("href"))
                        .build();

                news.getPosts().add(getPost);
            }
            //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

            List<FixtureDTO> fixtureDTOList = new ArrayList<>();

            List<Fixtures> fixtures = fixtureService.getListToMain();
            for (Fixtures fixture : fixtures) {
                FixtureDTO dto = FixtureDTO.builder()
                        .id(fixture.getId())
                        .league(fixture.getLeague())
                        .date(fixture.getDate())
                        .home(fixture.getHome())
                        .away(fixture.getAway())
                        .stadium(fixture.getStadium())
                        .isLive(fixture.getIsLive())
                        .homeScore(fixture.getHomeScore())
                        .awayScore(fixture.getAwayScore())
                        .time(fixture.getTime())
                        .homeAllocation(fixture.getHomeAllocation())
                        .awayAllocation(fixture.getAwayAllocation())
                        .drawAllocation(fixture.getDrawAllocation())
                        .build();

                fixtureDTOList.add(dto);
            }

            Users users = userService.findById(userDetails.getUser().getId());

            UserToMainDTO user = UserToMainDTO.builder()
                    .nickname(users.getNickname())
                    .betPoint(users.getBetPoint())
                    .firstTeam(users.getFirstTeam())
                    .secondTeam(users.getSecondTeam())
                    .thirdTeam(users.getThirdTeam())
                    .build();

            MainDTO mainDTO = MainDTO.builder()
                    .user(user)
                    .fixtures(fixtureDTOList)
                    .news(news)
                    .build();

            return new ResponseEntity<>(mainDTO, HttpStatus.OK);
        } catch (Exception e) {

            Message message = new Message("메인 에러 " + e);

            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
