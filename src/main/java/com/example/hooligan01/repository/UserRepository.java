package com.example.hooligan01.repository;

import com.example.hooligan01.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByAccount(String account);

    Optional<Users> findByAccountAndPhoneNumber(String account, String phoneNumber);

    Optional<Users> findByNickname(String nickname);

    Optional<Users> findByNameAndBirth(String name, String birth);

    Optional<Users> findByIdAndAccount(UUID id, String account);
}
