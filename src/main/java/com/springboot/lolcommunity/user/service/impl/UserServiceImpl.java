package com.springboot.lolcommunity.user.service.impl;

import com.springboot.lolcommunity.user.dto.UserDto;
import com.springboot.lolcommunity.user.service.EmailService;
import com.springboot.lolcommunity.user.service.UserService;
import com.springboot.lolcommunity.config.security.JwtTokenProvider;
import com.springboot.lolcommunity.user.entity.User;
import com.springboot.lolcommunity.user.repository.UserRepository;
import java.util.Collections;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserRepository userRepository;
    public PasswordEncoder passwordEncoder;
    public EmailService emailService;
    public JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public UserDto.SignResultDto signUp(String email, String password, String name) {
        User user;
        user = User.builder()
                    .email(email)
                    .nickname(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_USER")) // 회원가입 시 기본 권한
                    .build();
        userRepository.save(user);
        UserDto.SignResultDto result = UserDto.SignResultDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .build();
        return result;
    }

    @Override
    public UserDto.SignResultDto signIn(String email, String password) throws RuntimeException {
        User user = userRepository.getByEmail(email);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        UserDto.SignResultDto result = UserDto.SignResultDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .build();
        return result;
    }

    @Override
    public boolean findPw(String email) throws Exception{
        LOGGER.info("[findpw/service] 비밀번호 찾기 시도 email : {} ", email);
        User user =  userRepository.getByEmail(email);
        Optional<User> updateUser = Optional.ofNullable(user);
        if(updateUser.isPresent()){
            LOGGER.info("[findpw/service] 이메일 확인 완료 ");
            String code = emailService.sendSimpleMessage(email);
            user.setPassword(passwordEncoder.encode(code));
            userRepository.save(user);
            LOGGER.info("[findpw/service] 임시 비밀번호 전송 완료 ");
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean emailDuplicateCheck(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean nicknameDuplicateCheck(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public UserDto.SignResultDto updateUser(String email, String nickname, String password){
        User user = userRepository.getByEmail(email);
        String changePw = passwordEncoder.encode(password);
        user.setPassword(changePw);
        user.setNickname(nickname);
        userRepository.save(user);
        LOGGER.info("[updateUser] 회원정보 수정 완료 ");
        UserDto.SignResultDto result =  UserDto.SignResultDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .build();
        return result;
    }
}