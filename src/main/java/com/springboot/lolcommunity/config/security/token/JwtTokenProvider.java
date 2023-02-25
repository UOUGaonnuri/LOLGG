package com.springboot.lolcommunity.config.security.token;

import io.jsonwebtoken.*;

import java.security.Key;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
@Slf4j
public class JwtTokenProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService;

    private final String secretKey = "secretkeymanymanylongsecretkeyasdgafdghasmanymanylong";
    private final long tokenValidMillisecond = 1000L * 60 * 60; // 1시간만 토큰 유효
    private final long refreshTokenValidTime = 1000L * 60 * 60 * 144; // 1주일

    private Key key;

    @PostConstruct
    protected void init() { // 시크릿 키 초기화
        System.out.println(secretKey);
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        System.out.println(secretKey);
    }

    // Jwt 토큰 생성
    public String createToken(String userid, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userid);
        claims.put("roles", roles);

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행 일자
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond)) // set expire time
                .signWith(key, SignatureAlgorithm.HS256) // 암호화 알고리즘, secret 값 세팅
                .compact();
        String refreshToken = Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행 일자
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) // set expire time
                .signWith(key, SignatureAlgorithm.HS256) // 암호화 알고리즘, secret 값 세팅
                .compact();
        LOGGER.info("[createToken] 토큰 생성 완료");
        return token;
    }

    // 인증 정보 조회
    public Authentication getAuthentication(String token) {
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        Claims claims = parseClaims(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}",
                userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    // 회원 구별 정보 추출
    public String getUsername(String token) {
        String info = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody()
                .getSubject();
        return info;
    }
    // 토큰 값 추출
    public String resolveToken(HttpServletRequest request) {
        LOGGER.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("X-AUTH-TOKEN"); // api 요청을 보낼 때 헤더에 토큰을 포함해 보낸다.
    }


    //토큰 유효 체크
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            LOGGER.info("[validateToken] 토큰 유효 체크 예외 발생");
            return false;
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}