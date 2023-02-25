package com.springboot.lolcommunity.config.security;

import com.springboot.lolcommunity.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static Long getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getName() == null){
            throw new RuntimeException("로그인 정보가 없습니다.");
        }
        Long LoginId = Long.parseLong(authentication.getName());
        return LoginId;
    }
}