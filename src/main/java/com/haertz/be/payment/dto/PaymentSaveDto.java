package com.haertz.be.payment.dto;

import com.haertz.be.payment.entity.PaymentMethod;
import com.haertz.be.payment.entity.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PaymentSaveDto {
    //카카오페이, 계좌이체에서 모두 사용하는 결제내역 저장을 위한 dto
    private Long userId;               // 회원 ID
    private Long reservationId;        // 예약 ID
    private BigDecimal totalAmount;    // 결제 금액
    private String partnerOrderId;     // 외부 결제 서비스에서 사용하는 주문 ID-> 디자이너 스케쥴테이블에 있는 id
    private String paymentTransaction; // 결제 트랜잭션 ID
    private PaymentMethod paymentMethod;  // 결제 방식 (카카오페이, 계좌이체 등)
    private PaymentStatus paymentStatus;
    private Date paymentDate;
}
