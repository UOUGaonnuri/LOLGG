package com.springboot.lolcommunity.config.security;

import com.springboot.lolcommunity.config.security.token.JwtAuthenticationFilter;
import com.springboot.lolcommunity.config.security.token.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfiguration{

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().disable() //Http basic Auth  기반으로 로그인 인증창이 뜸.  disable 시에 인증창 뜨지 않음.
                .csrf().disable() //rest api이므로 csrf 보안이 필요없으므로 disable처리. 서버에 인증 정보를 보관하지 않기 때문

                .sessionManagement()
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/users/**").permitAll() // 일단 권한은 지정  X
                .antMatchers("/board/**").permitAll() // 일단 권한은 지정  X
                .antMatchers("/reply/**").permitAll() // 일단 권한은 지정  X
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("**exception**").permitAll()
                .anyRequest().hasRole("ADMIN")
                .and()
                .exceptionHandling() // 권한이 없는 사용자 처리
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling() // 인증되지 않은 사용자의 리소스에 대한 접근 처리
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }


    @Bean
    public WebSecurityCustomizer configure() {
        return(web) -> web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/users/exception");
    }
}