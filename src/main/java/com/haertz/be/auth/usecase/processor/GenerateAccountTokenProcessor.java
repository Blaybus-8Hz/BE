package com.haertz.be.auth.usecase.processor;

import com.haertz.be.auth.dto.response.AccountTokenDto;
import com.haertz.be.auth.entity.User;
import com.haertz.be.auth.service.RefreshTokenRedisService;
import com.haertz.be.common.annotation.Processor;
import com.haertz.be.common.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import static com.haertz.be.common.constant.StaticValue.ACCESS_TOKEN;
import static com.haertz.be.common.constant.StaticValue.REFRESH_TOKEN;

@Processor
@RequiredArgsConstructor
public class GenerateAccountTokenProcessor {
    private final JwtProvider jwtProvider;
    private final CookieProcessor cookieProcessor;
    private final RefreshTokenRedisService refreshTokenRedisService;

    // [유저]를 받아서, 액세스/리프레쉬 토큰을 만들고 AccountTokenDto를 만든다.
    public AccountTokenDto createToken(User user, HttpServletResponse response){
        String accessToken = jwtProvider.generateToken(user.getUserId(),user.getAuthInfo().getRole().getValue(),ACCESS_TOKEN);
        String refreshToken = jwtProvider.generateToken(user.getUserId(),user.getAuthInfo().getRole().getValue(), REFRESH_TOKEN);

        refreshTokenRedisService.save(user.getUserId(), refreshToken); // redis에 userId와 refreshToken 저장
        cookieProcessor.setRefreshTokenCookie(response, refreshToken);

        return AccountTokenDto.of(accessToken);
    }

    // [유저와 리프레쉬 토큰]을 받아서, 액세스 토큰을 만들고, AccountTokenDto을 만든다.
    public AccountTokenDto refreshToken(User user, String refreshToken, HttpServletResponse response){
        String accessToken = jwtProvider.generateToken(user.getUserId(),user.getAuthInfo().getRole().getValue(),ACCESS_TOKEN);

        refreshTokenRedisService.save(user.getUserId(), refreshToken); // redis refreshToken exp 갱신
        cookieProcessor.setRefreshTokenCookie(response, refreshToken);

        return AccountTokenDto.of(accessToken);

    }
}