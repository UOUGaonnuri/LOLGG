package com.springboot.lolcommunity.user.controller;


import com.springboot.lolcommunity.user.dto.UserDto;
import com.springboot.lolcommunity.user.entity.User;
import com.springboot.lolcommunity.user.repository.UserRepository;
import com.springboot.lolcommunity.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Autowired
    UserControllerTest(UserService userService, UserRepository userRepository){
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void signUp() {
        UserDto.SignUpRequestDto user1;
        user1 = UserDto.SignUpRequestDto.builder()
                .email("test1@email.com")
                .nickname("test1")
                .password("testpw")
                .build();

        UserDto.SignResultDto savedUser = userService.signUp(user1.getEmail(), user1.getPassword(), user1.getNickname());

        assertNotNull(savedUser);
    }

    @Test
    @DisplayName("로그인 테스트")
    public void signIn() {
        UserDto.SignUpRequestDto user1;
        user1 = UserDto.SignUpRequestDto.builder()
                .email("test1@email.com")
                .nickname("test1")
                .password("testpw")
                .build();
        UserDto.SignResultDto savedUser = userService.signUp(user1.getEmail(), user1.getPassword(), user1.getNickname());
        UserDto.SignInRequestDto user;
        user = UserDto.SignInRequestDto.builder()
                .email("test1@email.com")
                .password("testpw")
                .build();

        UserDto.SignInResultDto result = userService.signIn(user.getEmail(), user.getPassword());

        assertNotNull(result);
    }
    @Test
    public void emailDuplicateCheck() {
        UserDto.SignUpRequestDto user1;
        user1 = UserDto.SignUpRequestDto.builder()
                .email("test1@email.com")
                .nickname("test1")
                .password("testpw")
                .build();
        UserDto.SignResultDto savedUser = userService.signUp(user1.getEmail(), user1.getPassword(), user1.getNickname());

        boolean result = userService.emailDuplicateCheck(savedUser.getEmail());

        assertEquals(result, 1);

    }

    @Test
    @DisplayName("닉네임 중복 검사")
    public void nicknameDuplicateCheck() {
        UserDto.SignUpRequestDto user1;
        user1 = UserDto.SignUpRequestDto.builder()
                .email("test1@email.com")
                .nickname("test1")
                .password("testpw")
                .build();
        UserDto.SignResultDto savedUser = userService.signUp(user1.getEmail(), user1.getPassword(), user1.getNickname());

        boolean result1 = userService.nicknameDuplicateCheck("testtest");
        boolean result2 = userService.nicknameDuplicateCheck(savedUser.getNickname());


        assertTrue(result2);
    }



    @Test
    public void userUpdate() {
        UserDto.SignUpRequestDto user1;
        user1 = UserDto.SignUpRequestDto.builder()
                .email("test1@email.com")
                .nickname("test1")
                .password("testpw")
                .build();
        UserDto.SignResultDto savedUser = userService.signUp(user1.getEmail(), user1.getPassword(), user1.getNickname());
        UserDto.UserUpdateDto user = UserDto.UserUpdateDto.builder()
                .email("test1@email.com")
                .nickname("change")
                .password("testpwchange")
                .build();

        userService.updateUser(user.getEmail(),user.getNickname(),user.getPassword());
        String nickname = userRepository.getByEmail("test1@email.com").getNickname();
        assertEquals(nickname, "change");
    }
}