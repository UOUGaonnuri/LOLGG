package com.springboot.lolcommunity.board.service;

import com.springboot.lolcommunity.board.dto.*;
import com.springboot.lolcommunity.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostDto.PostResult postSave(PostDto.PostRequestDto postRequestDto);
    PostDto.PostGetResult postGet(Long pno);
    List<PostDto.PostListDto> postList(Integer page);
    Boolean postModify(PostDto.PostModifyDto postModifyDto);
    Boolean postDelete(Long pno, PostDto.PostDeleteDto postDeleteDto);
//    Boolean postHeart(PostDto.PostHeartDto postLikeDto);
}
