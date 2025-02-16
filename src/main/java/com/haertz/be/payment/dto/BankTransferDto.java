package com.haertz.be.payment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
//계좌이체 후 response반환을 위한 dto
public class BankTransferDto {
    private String googleMeetingLink;
    private Date created_at;
}
