package com.haertz.be.common.jwt.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Getter
public class JwtProperties {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-exp}")
    private Long accessTokenExp;

    @Value("${jwt.refresh-token-exp}")
    private Long refreshTokenExp;
}
