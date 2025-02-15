package com.haertz.be.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.haertz.be.auth.entity.Role.USER;
@Builder
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthInfo {

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private LoginType loginType; //로그인 타입

    @NotNull
    @Column
    private String email; //이메일

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus; //계정 상태

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role; //역할

    public static AuthInfo authInfoForSignUp(LoginType loginType, String email) {
        return AuthInfo.builder()
                .loginType(loginType)
                .email(email)
                .accountStatus(AccountStatus.MEMBER)
                .role(USER)
                .build();
    }

}