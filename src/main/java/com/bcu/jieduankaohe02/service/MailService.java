package com.bcu.jieduankaohe02.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    // 推荐从配置中读取，比如注入一个 @Value("${spring.mail.username}")
    private String fromEmail = "3601118595@qq.com";  // 这里替换为您实际使用的 QQ 邮箱

    public void sendActivationEmail(String to, String activationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);   // 设置正确的发件人
        message.setTo(to);
        message.setSubject("【购物商城】请激活您的账号");
        message.setText("请点击以下链接完成激活:\n\n"
                + "http://localhost:8080/activate?code=" + activationCode);
        mailSender.send(message);
    }
}