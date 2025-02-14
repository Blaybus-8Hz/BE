package com.haertz.be.common.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// id token을 decoded 했을 때 나오는 필드들
public class OIDCDecodePayload {
    private String iss; // 발급기관 식별자
    private String aud; // 애플리케이션 클라이언트 ID
    private String sub; // 사용자 ID
    private String email; // 사용자 이메일
    private String name; // 사용자 이름
}