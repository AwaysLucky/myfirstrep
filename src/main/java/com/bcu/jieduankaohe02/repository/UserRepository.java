package com.bcu.jieduankaohe02.repository;

import com.bcu.jieduankaohe02.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据邮箱查找用户
     */
    User findByEmail(String email);

    /**
     * 根据激活码查找用户
     */
    User findByActivationCode(String activationCode);

    /**
     * 判断用户ID是否存在（用于注册查重）
     */
    boolean existsById(Long userId);

    /**
     * 查找是否存在相同邮箱
     */
    boolean existsByEmail(String email);

    // 在UserRepository.java中添加
    @Query("SELECT u FROM User u WHERE u.username = :identifier OR u.email = :identifier")
    User findByUsernameOrEmail(@Param("identifier") String identifier);
}