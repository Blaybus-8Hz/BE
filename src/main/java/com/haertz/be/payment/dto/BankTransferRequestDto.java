package com.haertz.be.payment.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BankTransferRequestDto {
    //private String partner_order_id;
    //partner_order_id를 designerScheduleId로 변경
    //디자이너 스케줄 생성에 필요한 정보
    private Long designerId;           // 예약 가능한 디자이너의 ID
    private LocalDate bookingDate;     // 예약 날짜 (예: 2025-02-18)
    private LocalTime bookingTime;     // 예약 시작 시간 (예: 10:00:00)

    private String item_name;
    private String quantity;
    private String total_amount;
    private String tax_free_amount;
}

