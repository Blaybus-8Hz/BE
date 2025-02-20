package com.haertz.be.booking.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haertz.be.booking.entity.Booking;
import com.haertz.be.booking.entity.BookingStatus;
import com.haertz.be.booking.entity.MeetingType;
import com.haertz.be.payment.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResponse {

    private LocalDate bookingDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime bookingTime;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;
    private MeetingType meetingType;
    private String requestDetails;
    private Long designerId;
    private String designerShop;
    private String designerName;

    public static BookingResponse from(Booking booking) {
        return BookingResponse.builder()
                .bookingDate(booking.getBookingDate())
                .bookingTime(booking.getBookingTime())
                .bookingStatus(booking.getBookingStatus())
                .paymentStatus(booking.getPaymentStatus())
                .meetingType(booking.getMeetingType())
                .requestDetails(booking.getRequestDetails())
                .designerId(booking.getDesigner().getDesignerId())
                .designerShop(booking.getDesigner().getDesignerShop())
                .designerName(booking.getDesigner() != null ? booking.getDesigner().getDesignerName() : "Unknown") // Lazy Loading 방지
                .build();
    }
}



