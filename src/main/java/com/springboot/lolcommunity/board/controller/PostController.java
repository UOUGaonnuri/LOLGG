package com.springboot.lolcommunity.board.controller;

import com.springboot.lolcommunity.board.dto.*;
import com.springboot.lolcommunity.board.entity.Post;
import com.springboot.lolcommunity.board.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @GetMapping(value = "/page2")
    public ResponseEntity<List<PostDto.PostListDto>> postList2(@RequestParam Integer page){
        PostDto.PostListResultDto postListResultDto = postService.postList2(page);
        if(postListResultDto.getEnd()==10){
            return ResponseEntity.ok(postListResultDto.getPostList());
        }
        else{
            return ResponseEntity.status(210).body(postListResultDto.getPostList());
        }

    }
    @PostMapping(value = "/search/title")
    public Page<Post> postTitleSearch(@RequestParam Integer page, @RequestBody PostDto.PostSearchDto postSearchDto){
        return postService.postSearch(postSearchDto.getKeyword(), page);
    }

//    @PostMapping(value = "/search/writer")
//    public Page<Post> postWriterSearch(@RequestParam Integer page, @RequestBody PostDto.PostSearchDto postSearchDto){
//        return postService.postSearch(postSearchDto.getKeyword(), page);
//    }
//    @PostMapping(value = "/heart")
//    public ResponseEntity<Boolean> postHeart(@RequestBody PostDto.PostHeartDto postHeartDto) {
//        Boolean check = postService.postHeart(postHeartDto);
//        return ResponseEntity.ok(check);
//    }
}
