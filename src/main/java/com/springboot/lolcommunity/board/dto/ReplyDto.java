package com.springboot.lolcommunity.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReplyDto {
    private ReplyDto(){}

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReplyListDto {
        private long rno;
        private String writer;
        private String content;
        private LocalDateTime regDate;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReplyModifyDto {
        private String writer;
        private String content;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReplyRequestDto {
        private String writer;
        private String content;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReplyDeleteDto {
        private String writer;
    }
}
