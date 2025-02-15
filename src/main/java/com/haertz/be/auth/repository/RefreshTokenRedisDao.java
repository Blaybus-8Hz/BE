package com.haertz.be.auth.repository;

import com.haertz.be.auth.entity.RefreshToken;
import com.haertz.be.common.annotation.RedisRepository;
import com.haertz.be.common.redis.BaseRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import static com.haertz.be.common.constant.StaticValue.REFRESH_TOKEN_PREFIX;


@RedisRepository
public class RefreshTokenRedisDao extends BaseRedisRepository<RefreshToken> {

    @Autowired
    public RefreshTokenRedisDao(RedisTemplate<String, RefreshToken> redisTemplate) {
        this.prefix = REFRESH_TOKEN_PREFIX; // 리프레시 토큰의 접두사 설정
        this.redisTemplate = redisTemplate;
    }

}