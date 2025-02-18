package com.haertz.be.booking.dto.response;

import com.haertz.be.booking.entity.MeetingType;
import com.haertz.be.payment.entity.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class BookingListResponse {

    private String designerName;
    private String designerShop;
    private String designerAccount; //계좌

    private LocalDate bookingDate;
    private LocalTime bookingTime;

    private MeetingType meetingStatus;
    private PaymentStatus paymentStatus;
}
