package com.example.hooligan01.repository;

import com.example.hooligan01.dto.UserBetPointDTO;
import com.example.hooligan01.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByAccount(String account);

    Optional<Users> findByAccountAndPhoneNumber(String account, String phoneNumber);

    Optional<Users> findByNickname(String nickname);

    Optional<Users> findByNameAndBirth(String name, String birth);

    Optional<Users> findByIdAndAccount(UUID id, String account);

    @Query("SELECT new com.example.hooligan01.dto.UserBetPointDTO(" +
            "b.id, f.date, f.home, f.away, " +
            "f.homeAllocation, f.awayAllocation, f.drawAllocation, b.win, p) " +
            "FROM Users u " +
            "JOIN u.bet b " +
            "JOIN b.fixtures f " +
            "JOIN u.points p " +
            "WHERE u.id = :userId")
    List<UserBetPointDTO> findAll(@Param("userId") UUID userId);
}
