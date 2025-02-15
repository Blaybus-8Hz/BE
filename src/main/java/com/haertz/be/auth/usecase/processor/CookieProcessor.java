package com.haertz.be.auth.usecase.processor;

import com.haertz.be.common.annotation.Processor;
import com.haertz.be.common.jwt.properties.JwtProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Processor
@RequiredArgsConstructor
public class CookieProcessor {

    private final JwtProperties jwtProperties;
    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true); // xss 공격 방어
        refreshTokenCookie.setSecure(false); // 추후 true로 변경
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(Math.toIntExact(jwtProperties.getRefreshTokenExp()));
        response.addCookie(refreshTokenCookie);
    }

    public void clearRefreshTokenCookie(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setHttpOnly(true); // xss 공격 방어
        refreshTokenCookie.setSecure(false); // 추후 true로 변경
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);
    }
}


