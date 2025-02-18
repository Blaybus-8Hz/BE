package com.haertz.be.payment.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BankTransferCancelRequestDto {
    private Long paymentId;
}
