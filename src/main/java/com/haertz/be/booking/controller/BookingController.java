package com.haertz.be.booking.controller;

import com.haertz.be.booking.entity.Booking;
import com.haertz.be.booking.service.BookingService;
import com.haertz.be.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Tag("")
@RestController
@RequiredArgsConstructor
class BookingController {

    private final BookingService bookingService;
    @Operation(summary = "사용자의 다가오는 예약 내역을 조회합니다.")
    @GetMapping("/api/current/{userId}")
    public SuccessResponse<Object> getBookings(@PathVariable("userId") Long userId){
        List<Booking> bookingList = bookingService.getCurrentBookings(userId);

        return SuccessResponse.of(bookingList);
    }

    @Operation(summary = "사용자의 지난 예약 내역을 조회합니다.")
    @GetMapping("/api/past/{userId}")
    public SuccessResponse<Object> getPastBookings(@PathVariable("userId") Long userId){
        List<Booking> bookingList = bookingService.getPastBookings(userId);

        return SuccessResponse.of(bookingList);
    }
}
