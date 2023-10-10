package com.example.hooligan01.repository;

import com.example.hooligan01.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Users add(Users user);
    Users findByAccount(String account);
}
