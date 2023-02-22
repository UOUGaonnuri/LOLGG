package com.springboot.lolcommunity.board.service.impl;

import com.springboot.lolcommunity.board.dto.*;
import com.springboot.lolcommunity.board.entity.Post;
import com.springboot.lolcommunity.board.entity.Reply;
import com.springboot.lolcommunity.board.repository.PostRepository;
import com.springboot.lolcommunity.board.repository.ReplyRepository;
import com.springboot.lolcommunity.board.service.ReplyService;
import com.springboot.lolcommunity.user.entity.User;
import com.springboot.lolcommunity.user.repository.UserRepository;
import com.springboot.lolcommunity.user.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ReplyServiceImpl implements ReplyService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;

    @Autowired
    public ReplyServiceImpl(PostRepository postRepository, UserRepository userRepository
            , ReplyRepository replyRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.replyRepository = replyRepository;
    }
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public ReplyDto.ReplyResult replySave(Long pno, ReplyDto.ReplyRequestDto replyRequestDto){
        User user = userRepository.getByEmail(replyRequestDto.getWriter());
        Post post = postRepository.getByPno(pno);
        Reply reply = Reply.builder()
                .writer(user)
                .content(replyRequestDto.getContent())
                .post(post)
                .build();
        replyRepository.save(reply);
        ReplyDto.ReplyResult result = ReplyDto.ReplyResult.builder()
                .content(reply.getContent())
                .writer(reply.getWriter().getNickname())
                .build();
        return result;
    }
    public Boolean replyModify(Long rno, ReplyDto.ReplyModifyDto replyModifyDto){
        Reply reply = replyRepository.getByRno(rno);
        if(replyModifyDto.getWriter().equals(reply.getWriter().getEmail())){
            reply.setContent(replyModifyDto.getContent());
            replyRepository.save(reply);
            return true;
        }
        else{
            LOGGER.info("[replyModify] 회원 정보가 일치하지 않음");
            return false;
        }
    }
    public Boolean replyDelete(Long rno, ReplyDto.ReplyDeleteDto replyDeleteDto){
        Reply reply = replyRepository.getByRno(rno);
        if(replyDeleteDto.getWriter().equals(reply.getWriter().getEmail())){
            replyRepository.deleteByRno(rno);
            return true;
        }
        else{
            LOGGER.info("[replyDelete] 회원 정보가 일치하지 않음");
            return false;
        }
    }
    public List<ReplyDto.ReplyListDto> replyList(){
        List<Reply> replies = replyRepository.findAll(Sort.by(Sort.Direction.DESC, "pno"));
        List<ReplyDto.ReplyListDto> replyList = new ArrayList<>();
        for(Reply reply : replies){
            ReplyDto.ReplyListDto replyListDto = ReplyDto.ReplyListDto.builder()
                    .rno(reply.getRno())
                    .writer(reply.getWriter().getNickname())
                    .content(reply.getContent())
                    .regDate(reply.getRegDate())
                    .build();
            replyList.add(replyListDto);
        }
        return replyList;
    }
}
