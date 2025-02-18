package com.haertz.be.booking.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haertz.be.booking.entity.BookingStatus;
import com.haertz.be.booking.entity.MeetingType;
import com.haertz.be.payment.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResponse {

    private LocalDate bookingDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime bookingTime;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;
    private MeetingType meetingType;
    private String requestDetails;
    private String designerName;
}



