package com.haertz.be.booking.converter;

import com.haertz.be.booking.dto.request.BookingInfoRequest;
import com.haertz.be.booking.dto.request.PreBookingRequest;
import com.haertz.be.booking.dto.response.BookingResponse;
import com.haertz.be.booking.entity.Booking;
import com.haertz.be.booking.entity.BookingStatus;
import com.haertz.be.booking.entity.DesignerSchedule;
import com.haertz.be.designer.entity.Designer;
import com.haertz.be.payment.entity.PaymentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingConverter {
    public static Booking toBooking(BookingInfoRequest bookingInfo, Designer designer, Long userId, DesignerSchedule schedule) {
        return Booking.builder()
                .bookingDate(schedule.getBookingDate())
                .bookingTime(schedule.getBookingTime())
                .paymentStatus(schedule.getPaymentStatus())
                .bookingStatus(schedule.getPaymentStatus() == PaymentStatus.COMPLETED ? BookingStatus.CONFIRMED : BookingStatus.PENDING)
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

    public static DesignerSchedule toDesignerSchedule(Long userId, PreBookingRequest preBookingRequest, PaymentStatus paymentStatus){
        return DesignerSchedule.builder()
                .bookingDate(preBookingRequest.bookingDate())
                .bookingTime(preBookingRequest.bookingTime())
                .designerId(preBookingRequest.designerId())
                .paymentStatus(paymentStatus)
                .isTemporary(true)
                .userId(userId)
                .build();
    }

}


