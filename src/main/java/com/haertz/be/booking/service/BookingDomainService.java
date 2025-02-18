package com.haertz.be.booking.service;

import com.haertz.be.booking.adaptor.BookingAdaptor;
import com.haertz.be.booking.converter.BookingConverter;
import com.haertz.be.booking.dto.request.BookingInfoRequest;
import com.haertz.be.booking.entity.Booking;
import com.haertz.be.payment.entity.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingDomainService {
    private final BookingAdaptor bookingAdaptor;
    @Transactional
    public Booking book(BookingInfoRequest bookingInfo, Long userId, PaymentStatus paymentStatus){
        Booking book = BookingConverter.toBooking(bookingInfo, userId, paymentStatus);
        return bookingAdaptor.save(book);
    }


}
