package com.haertz.be.booking.converter;

import com.haertz.be.booking.dto.request.BookingInfoRequest;
import com.haertz.be.booking.dto.response.BookingResponse;
import com.haertz.be.booking.entity.Booking;
import com.haertz.be.booking.entity.BookingStatus;
import com.haertz.be.booking.entity.DesignerSchedule;
import com.haertz.be.designer.entity.Designer;
import com.haertz.be.payment.entity.PaymentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingConverter {
    public static Booking toBooking(BookingInfoRequest bookingInfo, Designer designer, Long userId, PaymentStatus paymentStatus) {
        return Booking.builder()
                .bookingDate(bookingInfo.bookingDate())
                .bookingTime(bookingInfo.bookingTime())
                .paymentStatus(paymentStatus)
                .bookingStatus(paymentStatus == PaymentStatus.COMPLETED ? BookingStatus.CONFIRMED : BookingStatus.PENDING)
                .meetingType(bookingInfo.meetingType())
                .requestDetails(bookingInfo.requestDetails())
                .userId(userId)
                .designer(designer)
                .build();
    }

    public static BookingResponse toBookingResponse(Booking booking, String designerName){
        return BookingResponse.builder()
                .bookingDate(booking.getBookingDate())
                .bookingTime(booking.getBookingTime())
                .bookingStatus(booking.getBookingStatus())
                .paymentStatus(booking.getPaymentStatus())
                .meetingType(booking.getMeetingType())
                .requestDetails(booking.getRequestDetails())
                .designerName(designerName)
                .build();
    }

    public static DesignerSchedule toDesignerSchedule(BookingInfoRequest bookingInfo, Long userId, PaymentStatus paymentStatus){
        return DesignerSchedule.builder()
                .bookingDate(bookingInfo.bookingDate())
                .bookingTime(bookingInfo.bookingTime())
                .designerId(bookingInfo.designerId())
                .paymentStatus(paymentStatus)
                .userId(userId)
                .build();
    }

}


