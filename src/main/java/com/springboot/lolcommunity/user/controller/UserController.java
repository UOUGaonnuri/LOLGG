package com.springboot.lolcommunity.user.controller;

import com.springboot.lolcommunity.user.dto.*;
import com.springboot.lolcommunity.user.entity.User;
import com.springboot.lolcommunity.user.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity signIn(@RequestBody UserDto.SignInRequestDto user)
            throws RuntimeException {
        LOGGER.info("[signIn/Controller] 로그인을 중. email : {}", user.getEmail());
        UserDto.SignResultDto result = userService.signIn(user.getEmail(), user.getPassword());
        LOGGER.info("[signIn/Controller] 로그인되었습니다. email : {}", user.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity signUp(@RequestBody UserDto.SignUpRequestDto user) {
        userService.signUp(user.getEmail(), user.getPassword(), user.getNickname());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/check/email")
    public ResponseEntity<Boolean> emailDuplicateCheck(@RequestBody UserDto.EmailRequestDto email){
        boolean result = userService.emailDuplicateCheck(email.getEmail());
        LOGGER.info("[emailDuplicateCheck/Controller] 이메일 중복확인 email : {}", email.getEmail());
        if(result){
            LOGGER.info("[emailDuplicateCheck/Controller] 이메일 중복");
            return ResponseEntity.badRequest().build();
        }
        else{
            LOGGER.info("[emailDuplicateCheck/Controller] 이메일 사용가능");
            return ResponseEntity.ok().build();
        }

    }

    @PostMapping(value = "/check/nickname")
    public ResponseEntity<Boolean> nicknameDuplicateCheck(@RequestBody UserDto.NicknameRequestDto nickname){
        boolean result = userService.nicknameDuplicateCheck(nickname.getNickname());
        LOGGER.info("[nicknameDuplicateCheck/Controller] 닉네임 중복확인 nickname : {}", nickname.getNickname());
        if(result){
            LOGGER.info("[nicknameDuplicateCheck/Controller] 닉네임 중복");
            return ResponseEntity.badRequest().build();
        }
        else{
            LOGGER.info("[nicknameDuplicateCheck/Controller] 닉네임 사용가능");
            return ResponseEntity.ok().build();
        }
    }


    @PostMapping("/findpw")
    public ResponseEntity findPw(@RequestBody UserDto.EmailRequestDto email) throws Exception {
        boolean result = userService.findPw(email.getEmail());
        LOGGER.info("[findPw/Controller] 비밀번호 찾기 email : {}", email);
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
        LOGGER.info("[userUpdate/Controller] 유저 정보 수정 email : {}",user.getEmail());
        userService.updateUser(user.getEmail(),user.getNickname(),user.getPassword());
        LOGGER.info("[userUpdate/Controller] 유저 정보 수정 완료");
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");
    }

    @ExceptionHandler(value = RuntimeException.class) // 예외처리
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus); // 클라이언트에게 응답을 보내는 코드
    }
}