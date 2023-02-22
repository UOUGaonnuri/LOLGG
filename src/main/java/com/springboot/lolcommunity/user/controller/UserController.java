package com.springboot.lolcommunity.user.controller;

import com.springboot.lolcommunity.user.dto.*;
import com.springboot.lolcommunity.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserDto.SignInResultDto> signIn(@RequestBody UserDto.SignInRequestDto user)
            throws RuntimeException {
        UserDto.SignInResultDto result = userService.signIn(user.getEmail(), user.getPassword());
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDto.SignResultDto> signUp(@RequestBody UserDto.SignUpRequestDto user) {
        UserDto.SignResultDto result = userService.signUp(user.getEmail(), user.getPassword(), user.getNickname());
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/check/email")
    public ResponseEntity<Boolean> emailDuplicateCheck(@RequestBody UserDto.EmailRequestDto email){
        boolean result = userService.emailDuplicateCheck(email.getEmail());
        if(result){
            LOGGER.info("[emailDuplicateCheck/Controller] 이메일 중복");
            return ResponseEntity.badRequest().build();
        }
        else{
            LOGGER.info("[emailDuplicateCheck/Controller] 이메일 사용가능");
            return ResponseEntity.ok(result);
        }

    }

    @PostMapping(value = "/check/nickname")
    public ResponseEntity<Boolean> nicknameDuplicateCheck(@RequestBody UserDto.NicknameRequestDto nickname){
        boolean result = userService.nicknameDuplicateCheck(nickname.getNickname());
        if(result){
            LOGGER.info("[nicknameDuplicateCheck/Controller] 닉네임 중복");
            return ResponseEntity.badRequest().build();
        }
        else{
            LOGGER.info("[nicknameDuplicateCheck/Controller] 닉네임 사용가능");
            return ResponseEntity.ok().build();
        }
    }
    @PostMapping(value = "/check/password")
    public ResponseEntity<UserDto.PasswordCheckResultDto> passwordCheck(@RequestBody UserDto.PasswordCheckRequestDto passwordCheckRequestDto){
        UserDto.PasswordCheckResultDto result = userService.passwordCheck(passwordCheckRequestDto.getToken(), passwordCheckRequestDto.getPassword());
        if(result.getEmail().equals("false")){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }


    @PostMapping("/findpw")
    public ResponseEntity findPw(@RequestBody UserDto.EmailRequestDto email) throws Exception {
        boolean result = userService.findPw(email.getEmail());
        if(result){
            LOGGER.info("[findPw/Controller] 임시 비밀번호 전송 완료");
            return ResponseEntity.ok().build();
        }
        else{
            LOGGER.info("[findPw/Controller] 이메일이 존재하지 않음.");
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity userUpdate(@RequestBody UserDto.UserUpdateDto user){
        userService.updateUser(user.getEmail(),user.getNickname(),user.getPassword());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");
    }
}