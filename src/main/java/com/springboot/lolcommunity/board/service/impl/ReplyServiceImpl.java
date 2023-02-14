package com.springboot.lolcommunity.board.service.impl;

import com.springboot.lolcommunity.board.dto.ReplyDeleteDto;
import com.springboot.lolcommunity.board.dto.ReplyListDto;
import com.springboot.lolcommunity.board.dto.ReplyModifyDto;
import com.springboot.lolcommunity.board.dto.ReplyRequestDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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

    public Reply replySave(Long pno, ReplyRequestDto replyRequestDto){
        LOGGER.info("[replySave] 댓글 작성 시도");
        User user = userRepository.getByEmail(replyRequestDto.getWriter());
        Post post = postRepository.getByPno(pno);
        Reply reply = Reply.builder()
                .writer(user)
                .content(replyRequestDto.getContent())
                .post(post)
                .build();
        replyRepository.save(reply).getRno();
        return reply;
    }
    public Boolean replyModify(Long rno, ReplyModifyDto replyModifyDto){
        LOGGER.info("[replyModify] 댓글 수정 시도");
        Reply reply = replyRepository.getByRno(rno);
        if(replyModifyDto.getWriter().equals(reply.getWriter().getEmail())){
            reply.setContent(replyModifyDto.getContent());
            replyRepository.save(reply);
            LOGGER.info("[replyModify] 댓글 수정 완료");
            return true;
        }
        else{
            LOGGER.info("[replyModify] 회원 정보가 일치하지 않음");
            return false;
        }
    }
    public Boolean replyDelete(Long rno, ReplyDeleteDto replyDeleteDto){
        LOGGER.info("[replyDelete] 댓글 삭제 시도");
        Reply reply = replyRepository.getByRno(rno);
        if(replyDeleteDto.getWriter().equals(reply.getWriter().getEmail())){
            replyRepository.deleteByRno(rno);
            LOGGER.info("[replyDelete] 댓글 삭제 완료");
            return true;
        }
        else{
            LOGGER.info("[replyDelete] 회원 정보가 일치하지 않음");
            return false;
        }
    }
    public List<ReplyListDto> replyList(){
        LOGGER.info("[replyList] 댓글 정보 조회");
        List<Reply> replies = replyRepository.findAll();
        List<ReplyListDto> replyList = new ArrayList<>();
        for(Reply reply : replies){
            ReplyListDto replyListDto = ReplyListDto.builder()
                    .rno(reply.getRno())
                    .writer(reply.getWriter().getNickname())
                    .content(reply.getContent())
                    .regDate(reply.getRegDate())
                    .build();
            replyList.add(replyListDto);
        }
        Collections.reverse(replyList);
        return replyList;
    }
}
