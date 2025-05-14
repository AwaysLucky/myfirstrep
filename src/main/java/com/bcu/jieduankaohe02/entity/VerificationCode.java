package com.bcu.jieduankaohe02.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "verification_code")
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeId;

    private Long userId;
    private String codeType;
    private String code;
    private LocalDateTime expiryTime;

    // Getters and Setters
}