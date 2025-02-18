package com.haertz.be.payment.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BankTransferRequestDto {
    private String partner_order_id;
    private String item_name;
    private String quantity;
    private String total_amount;
    private String tax_free_amount;
}

