package com.haertz.be.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookingStatus {
    PENDING("PENDING"), // 계좌이체의 경우 입금 전, 예약 대기
    CONFIRMED("CONFIRMED"), // 예약 완료
    CANCELED("CANCELED"); // 예약 취소

    private final String value;
}
