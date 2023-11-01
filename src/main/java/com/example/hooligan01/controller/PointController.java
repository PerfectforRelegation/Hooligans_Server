package com.example.hooligan01.controller;

import com.example.hooligan01.entity.Bets;
import com.example.hooligan01.entity.Points;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.security.UserDetailsImpl;
import com.example.hooligan01.service.BetService;
import com.example.hooligan01.service.PointService;
import com.example.hooligan01.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

    private final PointService pointService;
    private final BetService betService;

    @PostMapping("/bet/{id}")
    public Points pointBetting(@PathVariable UUID id, @RequestBody Points point, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Bets bet = betService.getById(id);
        Users user = userDetails.getUser();

        point.setBets(bet);
        point.setUsers(user);

        if (pointService.save(point)) {

            if (point.getPick().equals(bet.getFixtures().getHome()))
                bet.setHomePoint(bet.getHomePoint() + point.getBetPoint());
            else
                bet.setAwayPoint(bet.getAwayPoint() + point.getBetPoint());

            betService.save(bet);

            return point;
        }
        else
            return null;
    }

    // 보상받기
    // true or false 로 버튼 활성화
//    @PutMapping("/reward/{id}")
//    public ResponseEntity<Objects> betResult(@PathVariable UUID id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//        Users user = userDetails.getUser();
//
//        return pointService.getReword(id, user);
//    }
}
