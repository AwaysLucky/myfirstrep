package com.bcu.jieduankaohe02.controller;

import com.bcu.jieduankaohe02.entity.User;
import com.bcu.jieduankaohe02.service.MailService;
import com.bcu.jieduankaohe02.service.UserService;
import com.bcu.jieduankaohe02.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    /**
     * 显示注册页面
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * 处理注册请求
     */
    @PostMapping("/register")
    public String processRegistration(
            @RequestParam("userId") Long userId,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            Model model) {

        // 查重判断：用户ID是否已存在
        if (userRepository.existsById(userId)) {
            model.addAttribute("error", "该用户ID已存在，请更换一个");
            return "register";  // 返回注册页面显示错误
        }

        // 查重判断：邮箱是否已存在
        if (userRepository.existsByEmail(email)) {
            model.addAttribute("error", "该邮箱已被注册");
            return "register";
        }

        // 创建新用户
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setPassword(password);  // 建议加密存储
        user.setEmail(email);
        user.setActivationCode(java.util.UUID.randomUUID().toString());
        user.setIsActive(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // 保存用户
        userService.registerUser(user);

        // 发送激活邮件
        try {
            mailService.sendActivationEmail(user.getEmail(), user.getActivationCode());
        } catch (Exception e) {
            model.addAttribute("error", "邮件发送失败，请稍后尝试重新发送激活邮件");
            return "register";
        }

        return "redirect:/login";
    }

    /**
     * 处理激活链接
     */
    @GetMapping("/activate")
    public String activateAccount(@RequestParam("code") String activationCode, Model model) {
        User user = userService.activateUser(activationCode);
        if (user != null) {
            model.addAttribute("message", "您的账号已成功激活！");
            return "activation_success";
        } else {
            model.addAttribute("error", "无效或过期的激活码");
            return "activation_error";
        }
    }

    /**
     * 显示登录页面
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // 在UserController.java中添加
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("error", "用户名或密码错误");
        return "login";
    }
}