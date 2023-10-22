package com.example.hooligan01.service;

import com.example.hooligan01.dto.BetsDTO;
import com.example.hooligan01.entity.Bets;
import com.example.hooligan01.repository.BetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BetService {

    private final BetRepository betRepository;

//    public Bets getOne(UUID id) {
//
//        return betRepository.findById(id).get();
//    }

    public List<BetsDTO> findList() {

        return betRepository.findAllWithTeam();
    }

    public Bets getById(UUID id) {

        return betRepository.findById(id).get();
    }

    public void save(Bets bet) {

        betRepository.save(bet);
    }
}
