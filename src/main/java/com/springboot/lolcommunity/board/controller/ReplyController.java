package com.springboot.lolcommunity.board.controller;

import com.springboot.lolcommunity.board.dto.*;
import com.springboot.lolcommunity.board.entity.Reply;
import com.springboot.lolcommunity.board.service.PostService;
import com.springboot.lolcommunity.board.service.ReplyService;
import com.springboot.lolcommunity.user.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        LOGGER.info("[replyList] 게시글 목록 조회");
        List<ReplyDto.ReplyListDto> replyList = replyService.replyList();
        LOGGER.info("[replyList] 게시글 목록 조회 완료");
        return replyList;
    }

    @PostMapping(value = "/{pno}/write")
    public Reply replySave(@PathVariable Long pno,@RequestBody ReplyDto.ReplyRequestDto replyRequestDto){
        Reply reply = replyService.replySave(pno, replyRequestDto);
        LOGGER.info("[replySave] 댓글 작성 완료");
        return reply;
    }

    @PutMapping(value = "/modify/{rno}")
    public Boolean replyModify(@PathVariable Long rno, @RequestBody ReplyDto.ReplyModifyDto replyModifyDto){
        LOGGER.info("[replyModify] 댓글 수정");
        boolean check = replyService.replyModify(rno, replyModifyDto);
        if(check){
            LOGGER.info("[replyModify] 댓글 수정 완료");
        }
        return check;
    }
    @DeleteMapping(value = "/delete/{rno}")
    public Boolean replyDelete(@PathVariable Long rno, @RequestBody ReplyDto.ReplyDeleteDto replyDeleteDto){
        LOGGER.info("[replyDelete] 댓글 삭제");
        boolean check = replyService.replyDelete(rno, replyDeleteDto);
        if(check){
            LOGGER.info("[replyDelete] 댓글 삭제 완료");
        }
        return check;
    }
}
