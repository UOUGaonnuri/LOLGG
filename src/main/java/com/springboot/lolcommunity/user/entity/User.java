package com.springboot.lolcommunity.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uno;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Column(nullable = false)
    private String nickname;

    @ElementCollection(fetch = FetchType.EAGER) // 테이블 생성, 부모 Entity에 의해 관리.
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)// x -> new SimpleGrantedAuthority(x)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @JsonProperty(access = Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @JsonProperty(access = Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @JsonProperty(access = Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
