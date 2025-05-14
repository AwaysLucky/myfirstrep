package com.bcu.jieduankaohe02.controller;

import com.bcu.jieduankaohe02.entity.User;
import com.bcu.jieduankaohe02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewProfile(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/update-username")
    public String updateUsername(@RequestParam("newUsername") String newUsername, Model model) {
        try {
            User updatedUser = userService.updateUsername(newUsername);

            // 👇 更新 Spring Security 的 Authentication
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    updatedUser.getUsername(),
                    updatedUser.getPassword(),
                    auth.getAuthorities()
            );
            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    auth.getCredentials(),
                    auth.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(newAuth); // 👈 更新登录信息

            model.addAttribute("user", updatedUser);
            model.addAttribute("successMessage", "用户名已成功修改为：" + updatedUser.getUsername());
        } catch (Exception e) {
            User currentUser = userService.getCurrentUser();
            model.addAttribute("user", currentUser);
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "profile";
    }
}