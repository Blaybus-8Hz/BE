package com.haertz.be.auth.exception;

import com.haertz.be.common.exception.base.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    NOT_SUPPORTED_LOGINTYPE_ERROR(400, "USER_400","지원하지 않는 로그인 타입입니다"),
    EMAIL_ALREADY_REGISTERED(409,"USER_409","이미 존재하는 이메일 주소입니다."),
    EXPIRED_REFRESH_TOKEN(401,"REFRESH_TOKEN_401","refresh_token이 만료되었습니다."),
    USER_NOT_FOUND(404, "USER_404","유저를 찾을 수 없습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;

}
