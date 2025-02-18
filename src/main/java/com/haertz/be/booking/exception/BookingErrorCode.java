package com.haertz.be.booking.exception;

import com.haertz.be.common.exception.base.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookingErrorCode implements BaseErrorCode {
    INVALID_BOOKING_DATE(400, "BOOKING_400_1", "유효한 예약 날짜가 아닙니다. [예약 가능 범위: 오늘 ~ 3개월 후 말일]"),
    INVALID_BOOKING_TIME(400, "BOOKING_400_2", "예약 시간은 현재 시간 이후여야 합니다."),
    INVALID_TIME_FORMAT(400, "BOOKING_400_3", "예약 시간은 30분 단위여야 합니다."),
    OUT_OF_AVAILABLE_TIME_RANGE(400, "BOOKING_400_4", "예약 가능한 시간 범위를 벗어났습니다."),

    BOOKING_NOT_FOUND(404, "BOOKING_404","예약 정보를 찾을 수 없습니다."),
    BOOKING_ALREADY_REGISTERED(409, "BOOKING_409_1", "이미 다른 사람이 예약한 시간대입니다."),
    BOOKING_ALREADY_CANCELED(409, "BOOKING_409_2", "이미 취소된 예약입니다."),
    DESIGNER_SCHEDULE_NOT_FOUND(404, "SCHEDULE_404","예약된 일정 정보를 찾을 수 없습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;

}

