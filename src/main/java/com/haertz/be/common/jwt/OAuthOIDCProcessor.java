package com.haertz.be.common.jwt;

import com.haertz.be.common.exception.GlobalErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.common.feign.oidc.OIDCPublicKeyDto;
import com.haertz.be.common.feign.oidc.OIDCPublicKeysResponse;
import com.haertz.be.common.jwt.dto.OIDCDecodePayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthOIDCProcessor {
    private final JwtOIDCProvider jwtOIDCProvider;

    /* ID 토큰 헤더에서 kid 추출하고, oidcPublicKeysResponse에서 kid와 일치하는 공개키 찾는다
    서명 검증 후, iss와 aud 검증 후, 페이로드를 디코딩함 */
    public OIDCDecodePayload getPayloadFromIdToken(String token, String iss, String aud, OIDCPublicKeysResponse oidcPublicKeysResponse) {
        String kid = jwtOIDCProvider.getKidFromToken(token); // 서명 검증을 위해 kid 추출

        OIDCPublicKeyDto oidcPublicKeyDto = oidcPublicKeysResponse.getKeys().stream()// oidcPublicKeysResponse에서 kid 와 일치하는 공개키 찾음
                .filter(o -> o.getKid().equals(kid))
                .findFirst()
                .orElseThrow(() -> new BaseException(GlobalErrorCode.INVALID_TOKEN));
        Jws<Claims> sigVerifiedJws = jwtOIDCProvider.getOIDCTokenJws(token, oidcPublicKeyDto.getN(), oidcPublicKeyDto.getE());
        return  jwtOIDCProvider.getOIDCTokenPayload(sigVerifiedJws, iss, aud);// 서명 검증 받은 Jws 객체를 iss와 aud 검증하고 페이로드 디코딩
    }
}