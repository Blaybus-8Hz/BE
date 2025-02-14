package com.haertz.be.auth.adaptor;

import com.haertz.be.auth.repository.TokenBlacklistRedisDao;
import com.haertz.be.common.annotation.Adaptor;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
// 액세스 토큰 블랙리스트 처리
public class TokenBlacklistRedisAdaptor {
    private final TokenBlacklistRedisDao tokenBlacklistRedisDao;

    public void save(String accessToken, Long exp) {
        tokenBlacklistRedisDao.saveWithExpiration(tokenBlacklistRedisDao.generateKey(accessToken),true, exp);
    }

    public boolean hasKey(String accessToken){
        return tokenBlacklistRedisDao.hasKey(accessToken);
    }

}
