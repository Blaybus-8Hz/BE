package com.haertz.be.auth.service;

import com.haertz.be.auth.adaptor.RefreshTokenRedisAdaptor;
import com.haertz.be.auth.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.haertz.be.common.constant.StaticValue.REFRESH_TOKEN_PREFIX;

@Service
@RequiredArgsConstructor // 레디스 RefreshToken infrastructure 서비스
public class RefreshTokenRedisService {
    private final RefreshTokenRedisAdaptor refreshTokenRedisAdaptor;

    @Value("${jwt.refresh-token-exp}")
    private Long refreshTokenExp;

    public void save(Long id, String token) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(id)
                .token(token)
                .build();
        refreshTokenRedisAdaptor.save(id, refreshToken, refreshTokenExp);
    }

    public Boolean checkToken(Long id, String token) {
        RefreshToken refreshToken = refreshTokenRedisAdaptor.findById(id);
        if (token.equals(refreshToken.getToken())) return true;
        else return false;
    }

    public void deleteByUserId(Long userId) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        refreshTokenRedisAdaptor.deleteByKey(key);
    }
}
