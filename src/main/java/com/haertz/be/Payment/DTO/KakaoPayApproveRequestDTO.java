package com.haertz.be.Payment.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoPayApproveRequestDTO {
    private String tid;
    private String partner_order_id;
    private String partner_user_id;
    private String pg_token;

}
