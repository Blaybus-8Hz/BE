package com.haertz.be.payment.exception;

import com.haertz.be.common.exception.base.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements BaseErrorCode {
    INVALID_PAYMENT_REQUEST(400, "PAYMENT_400", "잘못된 결제 요청입니다."),
    PAYMENT_NOT_FOUND(404, "PAYMENT_404", "결제 내역을 찾을 수 없습니다."),
    PAYMENT_STATUS_INVALID(400, "PAYMENT_STATUS_", "결제 상태가 유효하지 않습니다."),
    PAYMENT_PROCESSING_ERROR(500, "PAYMENT_500", "결제 처리 중 오류가 발생했습니다."),
    PAYMENT_CANCELLATION_ERROR(500, "PAYMENT_500_2", "결제 취소 처리 중 오류가 발생했습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}
