package com.example.hooligan01.service;

import com.example.hooligan01.repository.BettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BettingService {

    private final BettingRepository bettingRepository;
}
