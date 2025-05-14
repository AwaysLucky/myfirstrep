package com.bcu.jieduankaohe02.service;

import com.bcu.jieduankaohe02.entity.User;
import com.bcu.jieduankaohe02.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        System.out.println("=== 调试：登录请求 ===");
        System.out.println("输入的用户名/邮箱: " + usernameOrEmail);

        User user = userRepository.findByUsernameOrEmail( usernameOrEmail);
        if (user == null) {
            System.out.println("错误：用户不存在");
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        System.out.printf("数据库用户信息 - 用户名: %s | 密码: %s | 激活状态: %b%n",
                user.getUsername(), user.getPassword(), user.isActive());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}