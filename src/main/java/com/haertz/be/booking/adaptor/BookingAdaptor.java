package com.haertz.be.booking.adaptor;

import com.haertz.be.booking.entity.Booking;
import com.haertz.be.booking.repository.BookingRepository;
import com.haertz.be.common.annotation.Adaptor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Adaptor
@RequiredArgsConstructor
public class BookingAdaptor {
    private final BookingRepository bookingRepository;

    public Booking save(Booking booking){
        return bookingRepository.save(booking);
    }

    public Boolean existsByDesignerScheduleId(Long designerScheduleId){
        return bookingRepository.existsByDesignerScheduleId(designerScheduleId);
    }
}
