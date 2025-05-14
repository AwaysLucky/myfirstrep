package com.bcu.jieduankaohe02.service;

import com.bcu.jieduankaohe02.entity.User;
import com.bcu.jieduankaohe02.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;  // 注入密码编码器

    public User registerUser(User user) {
        // 直接存储明文密码（移除加密）
        // user.setPassword(passwordEncoder.encode(user.getPassword())); // 删除这行
        return userRepository.save(user);
    }

    public User activateUser(String activationCode) {
        User user = userRepository.findByActivationCode(activationCode);
        if (user != null) {
            user.setIsActive(true);
            user.setActivationCode(null);  // 激活后清除激活码
            return userRepository.save(user);
        }
        return null;
    }

    /**
     * 发送激活邮件给用户
     */
    public void sendActivationEmail(User user) {
        String activationCode = user.getActivationCode();
        String email = user.getEmail();

        if (activationCode != null && email != null) {
            mailService.sendActivationEmail(email, activationCode);
        } else {
            throw new IllegalArgumentException("用户邮箱或激活码为空，无法发送激活邮件");
        }
    }

    /**
     * 获取当前登录用户
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("用户未登录");
        }

        String identifier = authentication.getName();
        User user = userRepository.findByUsernameOrEmail(identifier);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        return user;
    }

    /**
     * 修改用户名
     */
    public User updateUsername(String newUsername) {
        User currentUser = getCurrentUser();

        if (findByUsername(newUsername) != null) {
            throw new RuntimeException("该用户名已被占用");
        }

        currentUser.setUsername(newUsername);
        return userRepository.save(currentUser);
    }

    private User findByUsername(String username) {
        return userRepository.findByUsernameOrEmail(username);
    }

}