package com.haertz.be.payment.dto;

import com.haertz.be.payment.entity.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
//계좌이체 후 response반환을 위한 dto
public class BankTransferDto {
    private Long paymentId;
    private String googleMeetingLink;
    private Date created_at;
    private PaymentStatus paymentstatus;
    private String designerScheduleId;
}
