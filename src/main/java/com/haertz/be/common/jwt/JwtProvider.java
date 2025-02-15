package com.haertz.be.common.jwt;

import com.haertz.be.common.exception.GlobalErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.common.jwt.dto.DecodedJwtToken;
import com.haertz.be.common.jwt.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.haertz.be.common.constant.StaticValue.ACCESS_TOKEN;
import static com.haertz.be.common.constant.StaticValue.REFRESH_TOKEN;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtProperties jwtProperties;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
    }

    private Jws<Claims> parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            throw new BaseException(GlobalErrorCode.EXPIRED_TOKEN);
        } catch (Exception e) {
            throw new BaseException(GlobalErrorCode.INVALID_TOKEN);
        }
    }

    //access , refresh token 만드는 함수
    public String generateToken(Long userId, String role, String type) {
        Date now = new Date();
        int exp = 0;
        if (type.equalsIgnoreCase(ACCESS_TOKEN)) {
            exp = jwtProperties.getAccessTokenExp();
        } else if (type.equalsIgnoreCase(REFRESH_TOKEN)) {
            exp = jwtProperties.getRefreshTokenExp();
        }

        return Jwts.builder()
                .issuer("RouteFinders")
                .subject(userId.toString())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + exp))
                .claim("type", type)
                .claim("role", role)
                .signWith(getSecretKey())
                .compact();

    }

    // 토큰 검증하는 함수
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //토큰 decoded 하는 함수
    public DecodedJwtToken decodedToken(String token, String type) {
        Claims claims = parseClaims(token).getPayload();
        checkType(claims, type);
        return DecodedJwtToken.builder()
                .userId(Long.valueOf(claims.getSubject()))
                .role(String.valueOf(claims.get("role")))
                .type(String.valueOf(claims.get("type")))
                .build();
    }

    private void checkType(Claims claims, String type) {
        if (type.equalsIgnoreCase(String.valueOf(claims.get("type")))) return;
        else throw new BaseException(GlobalErrorCode.NOT_MATCHED_TOKEN_TYPE);
    }

}