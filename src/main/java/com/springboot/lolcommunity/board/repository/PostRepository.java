package com.springboot.lolcommunity.board.repository;

import com.springboot.lolcommunity.board.entity.Post;
import com.springboot.lolcommunity.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post getByPno(Long pno);
    void deleteByPno(Long pno);
}
