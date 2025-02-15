package com.haertz.be.auth.usecase;

import com.haertz.be.auth.adaptor.UserAdaptor;
import com.haertz.be.auth.dto.response.AccountTokenDto;
import com.haertz.be.auth.entity.User;
import com.haertz.be.auth.service.RefreshTokenRedisService;
import com.haertz.be.auth.usecase.processor.GenerateAccountTokenProcessor;
import com.haertz.be.common.annotation.UseCase;
import com.haertz.be.common.exception.GlobalErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.common.jwt.JwtProvider;
import com.haertz.be.common.jwt.dto.DecodedJwtToken;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import static com.haertz.be.common.constant.StaticValue.REFRESH_TOKEN;


@UseCase
@RequiredArgsConstructor
public class RefreshTokenUseCase {
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final GenerateAccountTokenProcessor generateAccountTokenProcessor;
    private final JwtProvider jwtProvider;
    private final UserAdaptor userAdaptor;

    public AccountTokenDto execute(String refreshToken, HttpServletResponse response) {
        DecodedJwtToken decodedJwtToken = jwtProvider.decodedToken(refreshToken, REFRESH_TOKEN);
        User user = userAdaptor.findById(decodedJwtToken.getUserId());

        if (refreshTokenRedisService.checkToken(user.getUserId(), refreshToken)) {
            return generateAccountTokenProcessor.refreshToken(user, refreshToken, response);
        } else throw new BaseException(GlobalErrorCode.INVALID_TOKEN);
    }

}