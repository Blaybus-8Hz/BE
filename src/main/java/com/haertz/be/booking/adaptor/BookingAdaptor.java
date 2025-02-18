package com.haertz.be.booking.adaptor;

import com.haertz.be.booking.entity.Booking;
import com.haertz.be.booking.exception.BookingErrorCode;
import com.haertz.be.booking.repository.BookingRepository;
import com.haertz.be.common.annotation.Adaptor;
import com.haertz.be.common.exception.base.BaseException;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class BookingAdaptor {
    private final BookingRepository bookingRepository;

    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Boolean existsByDesignerScheduleId(Long designerScheduleId) {
        return bookingRepository.existsByDesignerScheduleId(designerScheduleId);
    }

    public Booking findById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BaseException(BookingErrorCode.BOOKING_NOT_FOUND));
    }
}
