package com.haertz.be.payment.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoPayRequestDTO {
    //예약을 결제 완료 시점에 디비에 저장하는경우
    //private int cid;
    //private String partner_order_id; //기멩점 주문번호
    private String designerScheduleId;
    private String item_name;
    private String quantity;
    private String total_amount;
    private String tax_free_amount;

    /* 예약을 미리 디비에 저장하는 경우
    private Long reservationId; // 예약 ID (프론트에서 전달)
     */
}
