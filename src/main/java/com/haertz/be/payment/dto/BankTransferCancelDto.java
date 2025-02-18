package com.haertz.be.payment.dto;

import com.haertz.be.payment.entity.PaymentStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BankTransferCancelDto {
    private Long paymentId;
    private PaymentStatus paymentstatus;
}
