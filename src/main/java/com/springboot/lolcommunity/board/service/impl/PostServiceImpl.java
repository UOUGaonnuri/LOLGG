package com.springboot.lolcommunity.board.service.impl;

import com.springboot.lolcommunity.board.dto.*;
import com.springboot.lolcommunity.board.entity.Board;
import com.springboot.lolcommunity.board.entity.Post;
import com.springboot.lolcommunity.board.repository.BoardRepository;
import com.springboot.lolcommunity.board.repository.LikedRespository;
import com.springboot.lolcommunity.board.repository.PostRepository;
import com.springboot.lolcommunity.board.service.PostService;
import com.springboot.lolcommunity.config.security.SecurityUtil;
import com.springboot.lolcommunity.config.security.token.JwtTokenProvider;
import com.springboot.lolcommunity.user.entity.User;
import com.springboot.lolcommunity.user.repository.UserRepository;
import com.springboot.lolcommunity.user.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikedRespository likedRespository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository,
                           BoardRepository boardRepository, LikedRespository likedRespository,
                           JwtTokenProvider jwtTokenProvider) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.likedRespository = likedRespository;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    public PostDto.PostResult postSave(PostDto.PostRequestDto postRequestDto){
        String email = jwtTokenProvider.getUsername(postRequestDto.getToken());
        User user = userRepository.getByEmail(email);
        Board board = boardRepository.getByBno(1L);
        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .writer(user)
                .content(postRequestDto.getContent())
                .board(board)
                .build();
        postRepository.save(post);
        PostDto.PostResult result = PostDto.PostResult.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getWriter().getNickname())
                .build();
        return result;
    }

    public PostDto.PostGetResult postGet(Long pno){
        Post post = postRepository.getByPno(pno);
        PostDto.PostGetResult result = PostDto.PostGetResult.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getWriter().getNickname())
                .build();
        return result;
    }

    public Boolean postModify(PostDto.PostModifyDto postModifyDto){
        Post post = postRepository.getByPno(postModifyDto.getPno());
        post.setTitle(postModifyDto.getTitle());
        post.setContent(postModifyDto.getContent());
        postRepository.save(post);
        return true;
    }

    public Boolean postDelete(PostDto.PostDeleteDto postDeleteDto){
        Post post = postRepository.getByPno(postDeleteDto.getPno());
        if(postDeleteDto.getWriter().equals(post.getWriter().getNickname())){
            postRepository.deleteByPno(postDeleteDto.getPno());
            return true;
        }
        else{
            LOGGER.info("[postModify] 회원 정보가 일치하지 않음");
            return false;
        }

    }

    public List<PostDto.PostListDto> postList(Integer page){
        Sort sort = Sort.by("pno").descending();
        PageRequest pageRequest = PageRequest.of(page, 10, sort);
        Page<Post> result = postRepository.findAll(pageRequest);
        List<PostDto.PostListDto> postList = new ArrayList<>();
        for(Post post : result) {
            PostDto.PostListDto postListDto = PostDto.PostListDto.builder()
                    .pno(post.getPno())
                    .title(post.getTitle())
                    .writer(post.getWriter().getNickname())
                    .regDate(post.getRegDate())
                    .build();
            postList.add(postListDto);
        }
        return postList;
    }

//    public Boolean postHeart(PostDto.PostHeartDto postHeartDto){
//        User user = userRepository.getByEmail(postHeartDto.getEmail());
//        Post post = postRepository.getByPno(postHeartDto.getPno());
//        if(likedRespository.findByUserAndPost(user, post).isPresent()){
//            return false;
//        }
//        Liked liked = Liked.builder()
//                .post(post)
//                .user(user)
//                .build();
//        likedRespository.save(liked);
//        return true;
//    }
}
