package com.haertz.be.auth.repository;

import com.haertz.be.common.annotation.RedisRepository;
import com.haertz.be.common.redis.BaseRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import static com.haertz.be.common.constant.StaticValue.BLACKlIST_PREFIX;


@RedisRepository
public class TokenBlacklistRedisDao extends BaseRedisRepository<Boolean> {

    @Autowired
    public TokenBlacklistRedisDao(RedisTemplate<String, Boolean> redisTemplate) {
        this.prefix = BLACKlIST_PREFIX;
        this.redisTemplate = redisTemplate; // id랑 리프레쉬 토큰
    }
}
