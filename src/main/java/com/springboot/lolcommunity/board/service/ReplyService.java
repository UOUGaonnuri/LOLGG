package com.springboot.lolcommunity.board.service;

import com.springboot.lolcommunity.board.dto.*;
import com.springboot.lolcommunity.board.entity.Reply;

import java.util.List;

public interface ReplyService {
    ReplyDto.ReplyResult replySave(Long pno, ReplyDto.ReplyRequestDto replyRequestDto);
    Boolean replyModify(Long bno, ReplyDto.ReplyModifyDto replyModifyDto);
    Boolean replyDelete(Long rno, ReplyDto.ReplyDeleteDto replyDeleteDto);
    List<ReplyDto.ReplyListDto> replyList(Long pno);
}
