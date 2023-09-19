package com.example.hooligan01.repository;

import com.example.hooligan01.entity.Boards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Boards, Long> {

    Optional<Boards> findById(Long id);
}