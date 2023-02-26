package com.springboot.lolcommunity.board.repository;

import com.springboot.lolcommunity.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post getByPno(Long pno);
    void deleteByPno(Long pno);
    Page<Post> findByTitleContaining(String keyword, Pageable paging);
}
