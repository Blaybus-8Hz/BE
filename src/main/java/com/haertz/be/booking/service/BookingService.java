package com.haertz.be.booking.service;

import com.haertz.be.auth.adaptor.UserAdaptor;
import com.haertz.be.auth.entity.User;
import com.haertz.be.booking.dto.response.BookingResponse;
import com.haertz.be.booking.entity.Booking;
import com.haertz.be.booking.repository.BookingRepository;
import com.haertz.be.common.utils.AuthenticatedUserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserAdaptor userAdaptor;
    private final AuthenticatedUserUtils userUtils;

    public List<BookingResponse> getPastBookings(){
        Long userId = userUtils.getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();
        return bookingRepository.findByUserId(userId).stream()
                .filter(booking -> {
                    LocalDateTime bookingDateTime = LocalDateTime.of(booking.getBookingDate(), booking.getBookingTime()); // ✅ LocalTime 그대로 사용
                    return bookingDateTime.isBefore(now);
                })
                .map(booking->BookingResponse.from(booking))
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getCurrentBookings(){
        Long userId = userUtils.getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();
        return bookingRepository.findByUserId(userId).stream()
                .filter(booking -> {
                    LocalDateTime bookingDateTime = LocalDateTime.of(booking.getBookingDate(), booking.getBookingTime());
                    return bookingDateTime.isAfter(now);  // 미래 예약
                })
                .map(booking->BookingResponse.from(booking))
                .collect(Collectors.toList());
    }

}
