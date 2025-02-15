package com.haertz.be.common.exception;

import com.haertz.be.common.exception.base.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode {

    DUPLICATED_KEY(200,"DUPLICATED_KEY_409_1", "중복된 값으로 인해 예외가 발생했습니다."), //사용자 입력 오류값
    TOKEN_BLACKLISTED(200,"TOKEN_401_6", "블랙리스트에 등록된 accessToken입니다."), //사용자 입력 오류값

    BAD_REQUEST_ERROR(400, "GLOBAL_400_1", "잘못된 요청입니다."),

    INVALID_HTTP_MESSAGE_BODY(400, "GLOBAL_400_2", "HTTP 요청 바디의 형식이 잘못되었습니다."),
    INVALID_REQUEST_CONTENT(400, "GLOBAL_400_3", "잘못된 데이터를 포함한 요청입니다."),
    ACCESS_DENIED_REQUEST(403, "GLOBAL_403", "해당 요청에 접근 권한이 없습니다."),
    HEADER_VALUE_NOT_FOUND(404,"GLOBAL_404_1","헤더에 값이 존재하지 않습니다."),
    UNSUPPORTED_HTTP_METHOD(405, "GLOBAL_405", "지원하지 않는 HTTP 메소드입니다."),
    SERVER_ERROR(500, "GLOBAL_500", "서버 내부에서 알 수 없는 오류가 발생했습니다."),

    NOT_AUTHENTICATED_ERROR(401, "SECURITY_401", "사용자가 인증되지 않았습니다."),

    INVALID_TOKEN(401, "TOKEN_401_1", "토큰이 유효하지 않습니다."),
    OAUTH_ACCESS_TOKEN_NOT_FOUND(401, "TOKEN_401_2","OAUTH ACCESS 토큰이 유효하지 않습니다."),
    INVALID_SIGNATURE_TOKEN(401, "TOKEN_401_3", "토큰의 서명 검증 과정에서 오류가 생겼습니다."),
    INCORRECT_ISSUER_TOKEN(401, "TOKEN_401_4", "토큰 발급처가 일치하지 않습니다."),
    EXPIRED_TOKEN(401, "TOKEN_401_5", "토큰이 만료되었습니다."),
    NOT_MATCHED_TOKEN_TYPE(401, "TOKEN_401_6", "토큰의 타입이 일치하지 않아 디코딩할 수 없습니다."),

    TOKEN_HEADER_NOT_FOUND(404,"TOKEN_404_1","토큰에 KID가 존재하지 않습니다."),

    PUBKEY_GENERATION_FAILED(400, "PUBLIC_KEY_400", "공개키를 생성하는데 실패했습니다."),

    REDIS_ID_NOT_FOUND(404, "REDIS_404", "REDIS ID가 존재하지 않습니다."),
    REDIS_SAVE_FAILED(404, "REDIS_404_2", "REDIS에서 값을 저장하는 과정에서 오류가 발생했습니다."),
    REDIS_DELETE_FAILED(500,"REDIS_500","REDIS에서 값을 삭제하는 과정에서 오류가 발생했습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;

}