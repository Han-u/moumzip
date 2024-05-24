package com.ssafy.web.global.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class SecretKeyInitializer {

    @Value ("${spring.jwt.token.secret-key:}")
    private String secretKey;

    @PostConstruct
    public void initialize() {
        // 외부에서 application.yml을 설정할 수 있기 때문에 null 혹은 isEmpty를 확인한다.
        if (secretKey == null || secretKey.isEmpty()) {
            this.secretKey = generateRandomKey();
        }
    }

    private String generateRandomKey() {
        byte[] keyBytes = new byte[64];
        new SecureRandom().nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }
}

// 서비스 시작 시에 secret-key 값을 랜덤한 값으로 생성