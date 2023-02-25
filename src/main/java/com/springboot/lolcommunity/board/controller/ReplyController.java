package com.springboot.lolcommunity.board.controller;

import com.springboot.lolcommunity.board.dto.*;
import com.springboot.lolcommunity.board.entity.Post;
import com.springboot.lolcommunity.board.repository.PostRepository;
import com.springboot.lolcommunity.board.service.PostService;
import com.springboot.lolcommunity.board.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    private final PostRepository postRepository;
    private final PostService postService;
    private final ReplyService replyService;
    @Autowired
    public ReplyController(PostRepository postRepository, PostService postService, ReplyService replyService){
        this.postRepository = postRepository;
        this.postService = postService;
        this.replyService = replyService;
    }

    @GetMapping(value = "/{pno}")
    public List<ReplyDto.ReplyListDto> replyList(@PathVariable Long pno) throws Exception{
        List<ReplyDto.ReplyListDto> replyList = replyService.replyList(pno);
        return replyList;
    }

    @PostMapping(value = "/write/{pno}")
    public ResponseEntity<ReplyDto.ReplyResult> replySave(@PathVariable Long pno, @RequestBody ReplyDto.ReplyRequestDto replyRequestDto){
        ReplyDto.ReplyResult result = replyService.replySave(pno, replyRequestDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/modify/{rno}")
    public ResponseEntity<Boolean> replyModify(@PathVariable Long rno, @RequestBody ReplyDto.ReplyModifyDto replyModifyDto){
        boolean check = replyService.replyModify(rno, replyModifyDto);
        return ResponseEntity.ok(check);
    }
    @DeleteMapping(value = "/delete/{rno}")
    public ResponseEntity<Boolean> replyDelete(@PathVariable Long rno, @RequestBody ReplyDto.ReplyDeleteDto replyDeleteDto){
        boolean check = replyService.replyDelete(rno, replyDeleteDto);
        return ResponseEntity.ok(check);
    }
}
