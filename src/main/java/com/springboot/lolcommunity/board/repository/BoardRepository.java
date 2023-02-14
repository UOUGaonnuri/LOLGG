package com.springboot.lolcommunity.board.repository;

import com.springboot.lolcommunity.board.entity.Board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board getByBno(Long Bno);

}
