package com.haertz.be.payment.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Amount {
    private int total;
    private int tax_free;
    private int vat;
    private int point;
    private int discount;
}
