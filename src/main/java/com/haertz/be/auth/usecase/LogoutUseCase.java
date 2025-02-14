package com.haertz.be.auth.usecase;

import com.haertz.be.auth.entity.User;
import com.haertz.be.auth.service.RefreshTokenRedisService;
import com.haertz.be.auth.service.TokenBlacklistRedisService;
import com.haertz.be.auth.usecase.processor.CookieProcessor;
import com.haertz.be.common.annotation.UseCase;
import com.haertz.be.common.utils.AuthenticatedUserUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class LogoutUseCase {
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final TokenBlacklistRedisService tokenBlacklistRedisService;
    private final AuthenticatedUserUtils userUtils;
    private final CookieProcessor cookieProcessor;


    public void execute(String accessToken, HttpServletResponse response){
        User user = userUtils.getCurrentUser();
        refreshTokenRedisService.deleteByUserId(user.getUserId());
        cookieProcessor.clearRefreshTokenCookie(response);
        tokenBlacklistRedisService.save(accessToken);

    }
}
