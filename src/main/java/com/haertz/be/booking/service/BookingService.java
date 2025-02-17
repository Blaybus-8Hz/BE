package com.haertz.be.booking.service;

import com.haertz.be.auth.adaptor.UserAdaptor;
import com.haertz.be.auth.entity.User;
import com.haertz.be.auth.exception.UserErrorCode;
import com.haertz.be.booking.entity.Booking;
import com.haertz.be.booking.repository.BookingRepository;
import com.haertz.be.common.exception.base.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserAdaptor userAdaptor;
    public List<Booking> getPastBookings(Long userId){
        User user = userAdaptor.findById(userId);
        LocalDateTime now = LocalDateTime.now();
        return user.getBookings().stream()
                .filter(booking -> {
                    LocalDateTime bookingDateTime = LocalDateTime.of(booking.getBookingDate(), booking.getBookingTime());
                    return bookingDateTime.isBefore(now);  // 과거 예약
                })
                .collect(Collectors.toList());
    }

    public List<Booking> getCurrentBookings(Long userId){
        User user = userAdaptor.findById(userId);
        LocalDateTime now = LocalDateTime.now();
        return user.getBookings().stream()
                .filter(booking -> {
                    LocalDateTime bookingDateTime = LocalDateTime.of(booking.getBookingDate(), booking.getBookingTime());
                    return bookingDateTime.isAfter(now);  // 미래 예약
                })
                .collect(Collectors.toList());
    }

}
