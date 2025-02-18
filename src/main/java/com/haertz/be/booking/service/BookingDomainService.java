package com.haertz.be.booking.service;

import com.haertz.be.booking.adaptor.BookingAdaptor;
import com.haertz.be.booking.converter.BookingConverter;
import com.haertz.be.booking.dto.request.BookingInfoRequest;
import com.haertz.be.booking.entity.Booking;
import com.haertz.be.booking.entity.BookingStatus;
import com.haertz.be.booking.entity.DesignerSchedule;
import com.haertz.be.booking.exception.BookingErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.designer.entity.Designer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingDomainService {
    private final BookingAdaptor bookingAdaptor;
    @Transactional
    public Booking book(BookingInfoRequest bookingInfo, Designer designer, Long userId, DesignerSchedule schedule){
        Booking book = BookingConverter.toBooking(bookingInfo, designer, userId, schedule);
        return bookingAdaptor.save(book);
    }
    @Transactional
    public void cancelBooking(Booking booking){
        if (!(booking.getBookingStatus().equals(BookingStatus.CANCELED))){
            booking.refundAndCancelBooking();
            bookingAdaptor.save(booking);
        } else throw new BaseException(BookingErrorCode.BOOKING_ALREADY_CANCELED);
    }

}
