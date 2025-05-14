package com.bcu.jieduankaohe02.repository;

import com.bcu.jieduankaohe02.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    VerificationCode findByUserIdAndCodeType(Long userId, String codeType);
}