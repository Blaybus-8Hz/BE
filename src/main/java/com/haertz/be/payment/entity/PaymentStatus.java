package com.haertz.be.payment.entity;

public enum PaymentStatus {
    PENDING,   // 결제 대기
    COMPLETED,// 결제 완료
    IN_PROGRESS, // 결제 진행 중
    REFUNDED; // 환불 상태
}
