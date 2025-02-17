package com.haertz.be.booking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Tag("")
@RestController
class BookingController {

    @Operation(summary = "사용자의 예약 내역을 조회합니다.")
    @GetMapping("/api/booking")
    public void getBookings(){

    }
}
