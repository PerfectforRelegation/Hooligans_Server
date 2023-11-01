package com.example.hooligan01.repository;

import com.example.hooligan01.dto.BoardsDTO;
import com.example.hooligan01.entity.Boards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Boards, Long> {

    Optional<Boards> findById(Long id);

    @Query("SELECT new com.example.hooligan01.dto.BoardsDTO(b.id, u.nickname, b.title, b.content, b.commentCount, b.heartCount, " +
            "b.view, b.modified, b.boardDate, b.filename, b.filepath) FROM Boards b JOIN b.user u")
    List<BoardsDTO> findAllWithNicknameAndCount();
}