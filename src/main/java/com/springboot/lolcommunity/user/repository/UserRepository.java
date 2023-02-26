package com.springboot.lolcommunity.user.repository;

import com.springboot.lolcommunity.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    User getByEmail(String email);
    User getByNickname(String nickname);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesByEmail(String email);
}
