package com.example.hooligan01.repository;

import com.example.hooligan01.entity.BoardComments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoardCommentRepository extends JpaRepository<BoardComments, UUID> {
}
