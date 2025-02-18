package com.haertz.be.payment.dto;

import com.haertz.be.payment.entity.PaymentStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoPayCancelDto {
    private String tid;
    private String cid;
    private PaymentStatus paymentstatus;
}
