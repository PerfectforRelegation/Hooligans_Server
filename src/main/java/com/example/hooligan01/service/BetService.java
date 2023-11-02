package com.example.hooligan01.service;

import com.example.hooligan01.dto.BetsDTO;
import com.example.hooligan01.dto.Message;
import com.example.hooligan01.entity.Bets;
import com.example.hooligan01.entity.Fixtures;
import com.example.hooligan01.entity.Points;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.repository.BetRepository;
import com.example.hooligan01.repository.PointRepository;
import com.example.hooligan01.repository.UserRepository;
import com.example.hooligan01.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BetService {

    private final BetRepository betRepository;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    public List<BetsDTO> findList() {

        return betRepository.findAllWithTeam();
    }

    public Bets getById(UUID id) {

        return betRepository.findById(id).get();
    }

    public void save(Bets bet) {

        betRepository.save(bet);
    }

    // 버튼 누를 수 있는 api 서비스
    public Boolean canGetReward(UUID id, UserDetailsImpl userDetails) {

        Optional<Bets> getBet = betRepository.findById(id);
        Users user = userDetails.getUser();

        Bets bet = null;
        if (getBet.isPresent())
            bet = getBet.get();
        assert bet != null; // 오류 시 Exception 호출

        Optional<Points> getPoint = pointRepository.findPointsByBetsIdAndUsers(bet.getId(), user);

        Points point = null;
        if (getPoint.isPresent())
            point = getPoint.get();
        assert point != null;

        if (bet.getWin() == null || point.getPick() == null)
            return false;

        // 리턴 값 true 면 버튼 활성화, Points 의 result 값이 false 이어야 활성화 -> 수취 못했다는 뜻
        return bet.getWin().equals(point.getPick()) && !point.isResult();
    }

    // 수취 api service
    public ResponseEntity<Object> getReward(UUID id, UserDetailsImpl userDetails) {

        Optional<Bets> getBet = betRepository.findById(id);
        Bets bet = null;
        if (getBet.isPresent())
            bet = getBet.get();
        assert bet != null; // 오류 시 Exception 호출

        Users user = userDetails.getUser();

        Optional<Points> getPoint = pointRepository.findPointsByBetsIdAndUsers(bet.getId(), user);
        Points point = null;
        if (getPoint.isPresent())
            point = getPoint.get();
        assert point != null;

        Fixtures fixture = bet.getFixtures();

        if (bet.getWin().equals(fixture.getHome()))
            bet.setAllocation(fixture.getHomeAllocation());
        else if (bet.getWin().equals(fixture.getAway()))
            bet.setAllocation(fixture.getAwayAllocation());
        else
            bet.setAllocation(fixture.getDrawAllocation());

        // 유저가 베팅을 했을 때 마이너스
        int updatePoint = (int) (user.getBetPoint() + (point.getBetPoint() * bet.getAllocation()));
        user.setBetPoint(updatePoint);

        point.setResult(true);

        userRepository.save(user);
        pointRepository.save(point);

        Message message = new Message(String.valueOf(updatePoint));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
