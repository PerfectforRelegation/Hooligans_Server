package com.example.hooligan01.service;

import com.example.hooligan01.entity.Users;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserServiceTest {
    private final MemoryUserRepository memoryUserRepository = new MemoryUserRepository();
    private final UserService userService = new UserService(memoryUserRepository);

    @Test
    void user_can_login() {
        Users user = Users.EMPTY;

        Users login = userService.login(user);

        assertNotNull(login);
    }
}