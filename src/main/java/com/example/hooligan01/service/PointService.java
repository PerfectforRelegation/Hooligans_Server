package com.example.hooligan01.service;

import com.example.hooligan01.dto.Message;
import com.example.hooligan01.entity.Bets;
import com.example.hooligan01.entity.Points;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.repository.BetRepository;
import com.example.hooligan01.repository.PointRepository;
import com.example.hooligan01.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final BetRepository betRepository;
    private final UserRepository userRepository;

    public boolean save(Points point) {

        if (point != null) {
            pointRepository.save(point);
            return true;
        } else
            return false;
    }

//    public ResponseEntity<Objects> getReword(UUID id, Users user) {
//
//        Optional<Points> point = pointRepository.findPointsByBetsIdAndUsers(id, user);
//
//        if (point.isEmpty()) {
//
//            Message message = new Message("배팅 안함");
//            return new ResponseEntity<>(message, HttpStatus.OK);
//        }
//
//        Optional<Bets> bet = betRepository.findById(id);
//
//        int allBetPoint = bet.get().getHomePoint() + bet.get().getAwayPoint();
//
//        if (point.get().getResult() == true) {
//            user.setBetPoint(user.getBetPoint() + (allBetPoint));
//        }
//
//    }
}
