package com.haertz.be.booking.controller;

import com.haertz.be.booking.dto.request.BookingInfoRequest;
import com.haertz.be.booking.dto.response.BookingResponse;
import com.haertz.be.booking.entity.Booking;
import com.haertz.be.booking.service.BookingDomainService;
import com.haertz.be.booking.service.BookingService;
import com.haertz.be.booking.usecase.BookUseCase;
import com.haertz.be.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "컨설팅 예약 API", description = "컨설팅 예약 관련 API 입니다.")
@RequestMapping("/api/booking")
public class BookingController {
    private final BookUseCase bookUseCase;
    private final BookingService bookingService;

    @Operation(summary = "헤어 컨설팅 일정 예약을 요청합니다.")
    @PostMapping
    public SuccessResponse<BookingResponse> book(@RequestBody @Valid BookingInfoRequest bookingInfoRequest){
        BookingResponse bookingResponse = bookUseCase.execute(bookingInfoRequest);
        return SuccessResponse.of(bookingResponse);
    }

    @Operation(summary = "사용자의 다가오는 예약 내역을 조회합니다.")
    @GetMapping("/api/current")
    public SuccessResponse<Object> getBookings(){
        List<Booking> bookingList = bookingService.getCurrentBookings();

        return SuccessResponse.of(bookingList);
    }

    @Operation(summary = "사용자의 지난 예약 내역을 조회합니다.")
    @GetMapping("/api/past/{userId}")
    public SuccessResponse<Object> getPastBookings(@PathVariable("userId") Long userId){
        List<Booking> bookingList = bookingService.getPastBookings();

        return SuccessResponse.of(bookingList);
    }
}
