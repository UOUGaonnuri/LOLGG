package com.springboot.lolcommunity.board.controller;

import com.springboot.lolcommunity.board.dto.*;
import com.springboot.lolcommunity.board.entity.Post;
import com.springboot.lolcommunity.board.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<PostDto.PostListDto> postList(@RequestParam Integer page){
        List<PostDto.PostListDto> postList = postService.postList(page);
        return postList;
    }

    @PostMapping(value = "/write")
    public ResponseEntity<PostDto.PostResult> postWrite(@RequestBody PostDto.PostRequestDto postRequestDto){
        PostDto.PostResult post = postService.postSave(postRequestDto);
        return ResponseEntity.ok(post);
    }

    @GetMapping(value = "/{pno}")
    public ResponseEntity<PostDto.PostGetResult> postGet(@PathVariable Long pno){
        PostDto.PostGetResult result = postService.postGet(pno);
        return ResponseEntity.ok(result);
    }

    @PutMapping (value = "/modify/")
    public ResponseEntity<Boolean> postModify(@RequestBody PostDto.PostModifyDto postModifyDto){
        boolean check = postService.postModify(postModifyDto);
        return ResponseEntity.ok(check);
    }

    @DeleteMapping(value = "/delete/{pno}")
    public ResponseEntity<Boolean> postDelete(@RequestBody PostDto.PostDeleteDto postDeleteDto){
        boolean check = postService.postDelete(postDeleteDto);
        if(check){
            return ResponseEntity.ok(check);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
//    @PostMapping(value = "/heart")
//    public ResponseEntity<Boolean> postHeart(@RequestBody PostDto.PostHeartDto postHeartDto) {
//        Boolean check = postService.postHeart(postHeartDto);
//        return ResponseEntity.ok(check);
//    }
}
