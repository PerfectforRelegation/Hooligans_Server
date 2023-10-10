package com.example.hooligan01.service;

import com.example.hooligan01.entity.Users;
import com.example.hooligan01.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {
    private Map<String, Users> userMap = new HashMap<>();

    @Override
    public Users add(Users user) {
        return userMap.put(user.getAccount(), user);
    }

    @Override
    public Users findByAccount(String account) {
        return userMap.get(account);
    }
}
