package com.springboot.lolcommunity.board.dto;

import com.springboot.lolcommunity.board.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PostDto {
    private PostDto(){}

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostListDto {
        private long pno;
        private String writer;
        private String title;
        private LocalDateTime regDate;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostModifyDto {
        private Long pno;
        private String title;
        private String content;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostRequestDto {
        private String token;
        private String title;
        private String content;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostResult {
        private String writer;
        private String title;
        private String content;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostGetResult {
        private String writer;
        private String title;
        private String content;
        private Long pno;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostDeleteDto {
        private Long pno;
        private String writer;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostHeartDto {
        private String email;
        private Long pno;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostListResultDto {
        private List<PostDto.PostListDto> postList;
        private Integer end;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostSearchDto {
        private String keyword;
    }
}
