package com.springboot.lolcommunity.board.repository;


import com.springboot.lolcommunity.board.entity.Post;
import com.springboot.lolcommunity.board.entity.Reply;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Reply getByRno(Long rno);
    List<Reply> findAllByPostOrderByRnoDesc(Post post);
    void deleteByRno(Long rno);
}
