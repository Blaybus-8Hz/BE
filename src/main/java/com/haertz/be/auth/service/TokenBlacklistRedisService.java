package com.haertz.be.auth.service;

import com.haertz.be.auth.adaptor.TokenBlacklistRedisAdaptor;
import com.haertz.be.common.exception.GlobalErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenBlacklistRedisService {
    private final TokenBlacklistRedisAdaptor tokenBlacklistRedisAdaptor;

    @Value("${jwt.access-token-exp}")
    private Long accessTokenExp;

    public void save(String accessToken) {
        tokenBlacklistRedisAdaptor.save(accessToken, accessTokenExp);
    }

    public void checkTokenBlacklisted(String accessToken) {
        if (tokenBlacklistRedisAdaptor.hasKey(accessToken)) {
            throw new BaseException(GlobalErrorCode.TOKEN_BLACKLISTED);
        }
    }
}
