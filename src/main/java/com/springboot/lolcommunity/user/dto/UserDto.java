package com.springboot.lolcommunity.user.dto;

import lombok.*;

public class UserDto {
    private UserDto(){}
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SignInRequestDto {
        private String email;
        private String password;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignResultDto {
        private String email;
        private String password;
        private String nickname;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignUpRequestDto {
        private String email;
        private String password;
        private String nickname;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserUpdateDto {
        private String email;
        private String password;
        private String nickname;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EmailRequestDto{
        private String email;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NicknameRequestDto{
        private String nickname;
    }

}
