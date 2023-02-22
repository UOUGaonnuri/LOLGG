package com.springboot.lolcommunity.board.service;

import com.springboot.lolcommunity.board.dto.*;
import com.springboot.lolcommunity.board.entity.Post;

import java.util.List;

public interface PostService {
    PostDto.PostResult postSave(PostDto.PostRequestDto postRequestDto);
    PostDto.PostResult postGet(Long pno);
    List<PostDto.PostListDto> postList();
    Boolean postModify(Long pno, PostDto.PostModifyDto postModifyDto);
    Boolean postDelete(Long pno, PostDto.PostDeleteDto postDeleteDto);
}
