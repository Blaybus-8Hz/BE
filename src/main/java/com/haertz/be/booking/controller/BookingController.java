package com.haertz.be.booking.controller;

import com.haertz.be.common.response.SuccessResponse;
import com.sun.net.httpserver.Authenticator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Tag("")
@RestController
class BookingController {

    @Operation(summary = "사용자의 다가오는 예약 내역을 조회합니다.")
    @GetMapping("/api/current")
    public SuccessResponse<Object> getBookings(){


        return SuccessResponse.of(null); //일단 이거를 null로 넣어두고
    }

    @Operation(summary = "사용자의 지난 예약 내역을 조회합니다.")
    @GetMapping("/api/past")
    public SuccessResponse<Object> getPastBookings(){

        return SuccessResponse.of(null);
    }
}
