package com.haertz.be.common.jwt.filter;

import com.haertz.be.auth.service.TokenBlacklistRedisService;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.common.jwt.FilterExceptionProcessor;
import com.haertz.be.common.jwt.JwtProvider;
import com.haertz.be.common.jwt.dto.DecodedJwtToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final FilterExceptionProcessor filterExceptionProcessor;
    private final TokenBlacklistRedisService tokenBlacklistRedisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String token = resolveToken(request); // 토큰 추출함
            if (token != null) {
                tokenBlacklistRedisService.checkTokenBlacklisted(token); // 블랙리스트 accessToken 검증
                if (jwtProvider.validateToken(token)) { // 토큰 유효성 검사
                    Authentication authentication = getAuthentication(token); // 인증 객체 생성
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            chain.doFilter(request, response); // 다음 필터로 요청 전달
        } catch (BaseException e) {
            filterExceptionProcessor.excute(response, e);
        }
    }


    private Authentication getAuthentication(String token) {
        DecodedJwtToken decodedJwtToken = jwtProvider.decodedToken(token,"accessToken");
        Long userId = decodedJwtToken.getUserId();
        String role = decodedJwtToken.getRole();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>(); // 사용자의 권한 목록을 생성합니다.
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        // 인증된 사용자 정보를 SecurityContext에 설정합니다.
        return new UsernamePasswordAuthenticationToken(userId, null, authorities);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
