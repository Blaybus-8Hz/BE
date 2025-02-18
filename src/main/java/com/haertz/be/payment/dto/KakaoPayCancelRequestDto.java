package com.haertz.be.payment.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoPayCancelRequestDto {
    private String tid;                  // 결제 승인 시 받은 결제 고유 번호
    private String partnerOrderId;       // 주문 번호
    private int cancelAmount;
    private int cancelTaxFreeAmount;     // 취소할 비과세 금액 (예: 0)
}
