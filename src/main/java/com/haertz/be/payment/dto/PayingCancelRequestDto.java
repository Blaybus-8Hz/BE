package com.haertz.be.payment.dto;

import lombok.*;

//카카오페이 결제도중 취소하는 요청을 보내기 위한 dto
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PayingCancelRequestDto {
    //private String tid;
    private Long designerScheduleId;
}
