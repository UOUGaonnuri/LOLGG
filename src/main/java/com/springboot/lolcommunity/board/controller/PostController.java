package com.springboot.lolcommunity.board.controller;

import com.springboot.lolcommunity.board.dto.*;
import com.springboot.lolcommunity.board.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class PostController {

    private final PostService postService;
    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping(value = "/page")
    public List<PostDto.PostListDto> postList(@PageableDefault(size = 15, sort = "pno",  direction = Sort.Direction.DESC) Pageable pageable){
        List<PostDto.PostListDto> postList = postService.postList(pageable);
        return postList;
    }

    @PostMapping(value = "/write")
    public ResponseEntity<PostDto.PostResult> postWrite(@RequestBody PostDto.PostRequestDto postRequestDto){
        PostDto.PostResult post = postService.postSave(postRequestDto);
        return ResponseEntity.ok(post);
    }

    @GetMapping(value = "/{pno}")
    public ResponseEntity<PostDto.PostResult> postGet(@PathVariable Long pno){
        PostDto.PostResult result = postService.postGet(pno);
        return ResponseEntity.ok(result);
    }

    @PutMapping (value = "/modify/{pno}")
    public ResponseEntity<Boolean> postModify(@PathVariable Long pno, @RequestBody PostDto.PostModifyDto postModifyDto){
        boolean check = postService.postModify(pno,postModifyDto);
        return ResponseEntity.ok(check);
    }

    @DeleteMapping(value = "/delete/{pno}")
    public ResponseEntity<Boolean> postDelete(@PathVariable Long pno, @RequestBody PostDto.PostDeleteDto postDeleteDto){
        boolean check = postService.postDelete(pno, postDeleteDto);
        return ResponseEntity.ok(check);
    }
}
