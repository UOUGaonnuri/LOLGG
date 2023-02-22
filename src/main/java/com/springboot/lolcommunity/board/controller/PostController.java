package com.springboot.lolcommunity.board.controller;

import com.springboot.lolcommunity.board.dto.*;
import com.springboot.lolcommunity.board.entity.Post;
import com.springboot.lolcommunity.board.service.PostService;
import com.springboot.lolcommunity.user.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class PostController {

    private final PostService postService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping(value = "/")
    public List<PostDto.PostListDto> postList(){
        List<PostDto.PostListDto> postList = postService.postList();
        return postList;
    }

    @PostMapping(value = "/write")
    public ResponseEntity postWrite(@RequestBody PostDto.PostRequestDto postRequestDto){
        PostDto.PostResult post = postService.postSave(postRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{pno}")
    public PostDto.PostResult postGet(@PathVariable Long pno){
        PostDto.PostResult result = postService.postGet(pno);
        return result;
    }

    @PutMapping (value = "/modify/{pno}")
    public Boolean postModify(@PathVariable Long pno, @RequestBody PostDto.PostModifyDto postModifyDto){
        boolean check = postService.postModify(pno,postModifyDto);
        return check;
    }

    @DeleteMapping(value = "/delete/{pno}")
    public Boolean postDelete(@PathVariable Long pno, @RequestBody PostDto.PostDeleteDto postDeleteDto){
        boolean check = postService.postDelete(pno, postDeleteDto);
        return check;
    }
}
