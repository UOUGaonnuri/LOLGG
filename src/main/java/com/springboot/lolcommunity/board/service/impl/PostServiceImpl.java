package com.springboot.lolcommunity.board.service.impl;

import com.springboot.lolcommunity.board.dto.*;
import com.springboot.lolcommunity.board.entity.Board;
import com.springboot.lolcommunity.board.entity.Post;
import com.springboot.lolcommunity.board.repository.BoardRepository;
import com.springboot.lolcommunity.board.repository.PostRepository;
import com.springboot.lolcommunity.board.service.PostService;
import com.springboot.lolcommunity.user.entity.User;
import com.springboot.lolcommunity.user.repository.UserRepository;
import com.springboot.lolcommunity.user.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Transactional
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, BoardRepository boardRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    public Post postSave(PostDto.PostRequestDto postRequestDto){
        LOGGER.info("[postSave] 게시글 작성 시도 email : {} ", postRequestDto.getWriter());
        User user = userRepository.getByEmail(postRequestDto.getWriter());
        Board board = boardRepository.getByBno(1L);
        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .writer(user)
                .content(postRequestDto.getContent())
                .board(board)
                .build();
        Long pno = postRepository.save(post).getPno();
        LOGGER.info("[postSave] 게시글 작성 완료 email : {}, pno : {}", postRequestDto.getWriter(), pno);
        return post;
    }

    public Boolean postModify(Long pno, PostDto.PostModifyDto postModifyDto){
        LOGGER.info("[postModify] 게시글 수정");
        Post post = postRepository.getByPno(pno);
        if(postModifyDto.getWriter().equals(post.getWriter().getEmail())){
            post.setTitle(postModifyDto.getTitle());
            post.setContent(postModifyDto.getContent());
            postRepository.save(post);
            LOGGER.info("[postModify] 게시글 수정 완료");
            return true;
        }
        else{
            LOGGER.info("[postModify] 회원 정보가 일치하지 않음");
            return false;
        }
    }

    public Boolean postDelete(Long pno, PostDto.PostDeleteDto postDeleteDto){
        LOGGER.info("[postDelete] 게시글 삭제");
        Post post = postRepository.getByPno(pno);
        if(postDeleteDto.getWriter().equals(post.getWriter().getEmail())){
            postRepository.deleteByPno(pno);
            LOGGER.info("[postDelete] 게시글 삭제 완료");
            return true;
        }
        else{
            LOGGER.info("[postModify] 회원 정보가 일치하지 않음");
            return false;
        }

    }

    public PostDto.PostResult postGet(Long pno){
        LOGGER.info("[postGet] 게시글 조회");
        Post post = postRepository.getByPno(pno);
        PostDto.PostResult result = PostDto.PostResult.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getWriter().getNickname())
                .build();
        LOGGER.info("[postGet] 게시글 조회 완료 pno : {}", pno);
        return result;
    }

    public List<PostDto.PostListDto> postList(){
        LOGGER.info("[postList/Controller] 게시글 조회");
        List<Post> posts = postRepository.findAll();
        List<PostDto.PostListDto> postList = new ArrayList<>();
        for(Post post : posts){
            PostDto.PostListDto postListDto = PostDto.PostListDto.builder()
                    .pno(post.getPno())
                    .title(post.getTitle())
                    .writer(post.getWriter().getNickname())
                    .regDate(post.getRegDate())
                    .build();
            postList.add(postListDto);
        }
        Collections.reverse(postList);
        LOGGER.info("[postList/Controller] 게시글 조회 완료");
        return postList;
    }



}
