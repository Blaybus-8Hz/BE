package com.haertz.be.payment.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoPayCancelDto {
    private String tid;
    private String cid;
}
