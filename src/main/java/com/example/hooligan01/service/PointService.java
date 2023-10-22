package com.example.hooligan01.service;

import com.example.hooligan01.entity.Points;
import com.example.hooligan01.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    @Autowired
    private PointRepository pointRepository;

    public boolean save(Points point) {

        if (point != null) {
            pointRepository.save(point);
            return true;
        } else
            return false;
    }
}
