package com.example.hooligan01.service;

import com.example.hooligan01.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BetService {

    @Autowired
    private BetRepository betRepository;


}
