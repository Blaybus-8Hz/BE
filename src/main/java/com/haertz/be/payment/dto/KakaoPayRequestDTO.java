package com.haertz.be.payment.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoPayRequestDTO {
    //예약을 결제 완료 시점에 디비에 저장하는경우
    //private int cid;
    //private String partner_order_id; //기멩점 주문번호
    //private String designerScheduleId;

    //디자이너 스케줄 생성에 필요한 정보
    private Long designerId;           // 예약 가능한 디자이너의 ID
    private LocalDate bookingDate;     // 예약 날짜 (예: 2025-02-18)
    private LocalTime bookingTime;     // 예약 시작 시간 (예: 10:00:00)

    //결제 관련 정보
    private String item_name;
    private String quantity;
    private String total_amount;
    private String tax_free_amount;
}
