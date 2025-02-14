package com.haertz.be.common.response;

import com.haertz.be.common.exception.base.BaseErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorResponse extends BaseResponse {
    private final int httpStatus;

    @Builder
    public ErrorResponse(boolean success, String code, String message, int httpStatus) {
        super(success, code, message);
        this.httpStatus = httpStatus;
    }

    // errorCode를 이용해 ErrorResponse 생성
    public static ErrorResponse fromErrorCode(BaseErrorCode errorCode) {
        return ErrorResponse.builder()
                .success(false)
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .httpStatus(errorCode.getHttpStatus())
                .build();
    }

    // errorCode와 커스텀 메시지를 이용해 ErrorResponse 생성
    public static ErrorResponse fromErrorCodeWithCustomMessage(BaseErrorCode errorCode, String customMessage) {
        return ErrorResponse.builder()
                .success(false)
                .code(errorCode.getCode())
                .message(customMessage)  // 커스텀 메시지를 사용
                .httpStatus(errorCode.getHttpStatus())
                .build();
    }
}
