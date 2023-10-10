package com.example.hooligan01.repository;

import com.example.hooligan01.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<Users, Long> {

}
