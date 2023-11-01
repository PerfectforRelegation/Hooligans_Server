package com.example.hooligan01.service;

import com.example.hooligan01.dto.Message;
import com.example.hooligan01.entity.Bets;
import com.example.hooligan01.entity.Points;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.repository.BetRepository;
import com.example.hooligan01.repository.PointRepository;
import com.example.hooligan01.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final BetRepository betRepository;
    private final UserRepository userRepository;

    public ResponseEntity<Object> saveBetting(UUID id, Points point, Users getUser) {

        Message message;

        try {

            Optional<Bets> bet = betRepository.findById(id);
            if (bet.isEmpty()) {
                message = new Message("PointService.saveBetting 에러 : 베팅 아이디 x");
                return new ResponseEntity<>(message, HttpStatus.OK);
            }

            Optional<Users> user = userRepository.findById(getUser.getId());
            if (user.isEmpty()) {
                message = new Message("PointService.saveBetting 에러 : 유저 아이디 x");
                return new ResponseEntity<>(message, HttpStatus.OK);
            }

            point.setBets(bet.get());
            point.setUsers(user.get());

            pointRepository.save(point);

            Bets updateBet = bet.get();

            if (point.getPick().equals(bet.get().getFixtures().getHome()))
                updateBet.setHomePoint(updateBet.getHomePoint() + point.getBetPoint());
            else
                updateBet.setAwayPoint(updateBet.getAwayPoint() + point.getBetPoint());

            betRepository.save(updateBet);

            return new ResponseEntity<>(point, HttpStatus.OK);

        } catch (Exception e) {

            message = new Message("PointService.saveBetting 에러 " + e);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
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
