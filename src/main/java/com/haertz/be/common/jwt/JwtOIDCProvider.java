package com.haertz.be.common.jwt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haertz.be.common.exception.GlobalErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.common.jwt.dto.OIDCDecodePayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@Component
@RequiredArgsConstructor
@Slf4j
//ID 토큰에서의 정보(클레임)을 추출한다.
public class JwtOIDCProvider {

    // JWT의 헤더에서 kid를 추출하는 메서드
    public String getKidFromToken(String token) {
        String[] splitToken = token.split("\\.");
        if (splitToken.length != 3) throw new BaseException(GlobalErrorCode.INVALID_TOKEN);

        String headerJson = new String(Base64.getUrlDecoder().decode(splitToken[0]));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode headerNode;
        try{
            headerNode = objectMapper.readTree(headerJson); // JSON으로 변환
        } catch (Exception e){
            throw new BaseException(GlobalErrorCode.TOKEN_HEADER_NOT_FOUND);
        }
        return headerNode.get("kid").asText(); // kid 반환
    }

    // 공개 키로 검증한 JWS 객체를 aud, iss 검증하고 페이로드 추출하고 페이로드 정보를 OIDCDecodedPayload로 반환
    public OIDCDecodePayload getOIDCTokenPayload(Jws<Claims> jws, String iss, String aud){
        Claims payload = validateClaims(jws, iss, aud);
        return new OIDCDecodePayload(
                payload.getIssuer(),
                payload.getAudience().toString(),
                payload.getSubject(),
                payload.get("email", String.class),
                payload.get("name", String.class)
        );
    }

    // 클레임을 검증하는 메서드
    public Claims validateClaims(Jws<Claims> jws, String iss, String aud) {
        Claims claims = jws.getPayload();
        if(!claims.getIssuer().equals(iss) || !claims.getAudience().contains(aud)){
            throw new BaseException(GlobalErrorCode.INVALID_TOKEN);
        }
        return claims;
    }

    // RSA 공개키를 사용하여 JWT을 파싱하여 서명 검증 후, 유효하면, 클레임을 포함한 JWS 객체를 반환함 : 서명 검증
    public Jws<Claims> getOIDCTokenJws(String token, String modulus, String exponent) {
        try {
            log.debug("Verifying JWT token with RSA public key: {}", token);
            return Jwts.parser()
                    .verifyWith(getRSAPublicKey(modulus, exponent)) // modulus, exponent: 공개키 구성 요소
                    .build()
                    .parseSignedClaims(token); // 공개키로 검증 후, 유효하면 페이로드와 서명 정보 반환
        } catch (ExpiredJwtException e) {
            log.error("Token has expired: {}", token);
            throw new BaseException(GlobalErrorCode.EXPIRED_TOKEN);
        } catch (Exception e) {
            log.error("Error verifying token: {}, Exception: {}", token, e.getMessage(), e);
            throw new BaseException(GlobalErrorCode.INVALID_SIGNATURE_TOKEN);
        }
    }

    // RSA 공개키를 생성하는 메서드
    private PublicKey getRSAPublicKey(String modulus, String exponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        log.debug("Generating RSA public key from modulus and exponent.");
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodeN = Base64.getUrlDecoder().decode(modulus); // Base64 URL 디코딩
        byte[] decodeE = Base64.getUrlDecoder().decode(exponent); // Base64 URL 디코딩
        BigInteger n = new BigInteger(1, decodeN);
        BigInteger e = new BigInteger(1, decodeE);

        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n, e);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        log.debug("Generated RSA public key: {}", publicKey);
        return publicKey;
    }
}
