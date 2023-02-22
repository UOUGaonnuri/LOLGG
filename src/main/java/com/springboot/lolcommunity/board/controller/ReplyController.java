package com.springboot.lolcommunity.board.controller;

import com.springboot.lolcommunity.board.dto.*;
import com.springboot.lolcommunity.board.entity.Reply;
import com.springboot.lolcommunity.board.service.PostService;
import com.springboot.lolcommunity.board.service.ReplyService;
import com.springboot.lolcommunity.user.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    private final PostService postService;
    private final ReplyService replyService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public ReplyController(PostService postService, ReplyService replyService){
        this.postService = postService;
        this.replyService = replyService;
    }

    @GetMapping(value = "/")
    public List<ReplyDto.ReplyListDto> replyList() throws Exception{
        List<ReplyDto.ReplyListDto> replyList = replyService.replyList();
        return replyList;
    }

    @PostMapping(value = "/{pno}/write")
    public ResponseEntity replySave(@PathVariable Long pno, @RequestBody ReplyDto.ReplyRequestDto replyRequestDto){
        ReplyDto.ReplyResult result = replyService.replySave(pno, replyRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/modify/{rno}")
    public Boolean replyModify(@PathVariable Long rno, @RequestBody ReplyDto.ReplyModifyDto replyModifyDto){
        boolean check = replyService.replyModify(rno, replyModifyDto);
        return check;
    }
    @DeleteMapping(value = "/delete/{rno}")
    public Boolean replyDelete(@PathVariable Long rno, @RequestBody ReplyDto.ReplyDeleteDto replyDeleteDto){
        boolean check = replyService.replyDelete(rno, replyDeleteDto);
        return check;
    }
}
