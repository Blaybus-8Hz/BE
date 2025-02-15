package com.haertz.be.auth.adaptor;

import com.haertz.be.auth.entity.RefreshToken;
import com.haertz.be.auth.exception.UserErrorCode;
import com.haertz.be.auth.repository.RefreshTokenRedisDao;
import com.haertz.be.common.annotation.Adaptor;
import com.haertz.be.common.exception.base.BaseException;
import lombok.RequiredArgsConstructor;


@Adaptor
@RequiredArgsConstructor
// RefreshTokenRedisDao를 사용하여 데이터 저장 및 조회
public class RefreshTokenRedisAdaptor {
    private final RefreshTokenRedisDao refreshTokenRedisDao;

    public void save(Long id, RefreshToken refreshToken, Long exp) {
        refreshTokenRedisDao.saveWithExpiration(refreshTokenRedisDao.generateKeyFromId(id), refreshToken, exp);
    }

    public RefreshToken findById(Long id) {
        return refreshTokenRedisDao.findById(id)
                .orElseThrow(() -> new BaseException(UserErrorCode.EXPIRED_REFRESH_TOKEN));
    }

    public void deleteByKey(String key) {
        refreshTokenRedisDao.deleteByKey(key);
    }
}
