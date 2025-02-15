package com.haertz.be.common.feign.oidc;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 공개키를 담는 dto
public class OIDCPublicKeyDto {
    private String kid;
    private String alg; // 알고리즘
    private String use;
    private String n; // modulus
    private String e; // exponent
}
