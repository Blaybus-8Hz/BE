package com.haertz.be.booking.controller;

import com.haertz.be.booking.dto.request.BookingInfoRequest;
import com.haertz.be.booking.dto.response.BookingResponse;
import com.haertz.be.booking.entity.Booking;
import com.haertz.be.booking.service.BookingDomainService;
import com.haertz.be.booking.service.BookingService;
import com.haertz.be.booking.usecase.BookUseCase;
import com.haertz.be.booking.usecase.GetAvailableDatesUseCase;
import com.haertz.be.booking.usecase.GetAvailableTimesUseCase;
import com.haertz.be.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "컨설팅 예약 API", description = "컨설팅 예약 관련 API 입니다.")
@RequestMapping("/api/booking")
public class BookingController {
    private final BookUseCase bookUseCase;
    private final GetAvailableDatesUseCase getAvailableDatesUseCase;
    private final GetAvailableTimesUseCase getAvailableTimesUseCase;

    private final BookingService bookingService;

    @Operation(summary = "헤어 컨설팅 일정 예약을 요청합니다.")
    @PostMapping
    public SuccessResponse<BookingResponse> book(@RequestBody @Valid BookingInfoRequest bookingInfoRequest) {
        BookingResponse bookingResponse = bookUseCase.execute(bookingInfoRequest);
        return SuccessResponse.of(bookingResponse);
    }

    @Operation(summary = "해당 디자이너의 3개월 내 예약 가능 날짜를 조회합니다.")
    @GetMapping("/available-dates")
    public SuccessResponse<Map<String, List<LocalDate>>> getAvailableDates(@RequestParam @Min(1) Long designerId) {
        List<LocalDate> availableDates = getAvailableDatesUseCase.execute(designerId);
        return SuccessResponse.of(Collections.singletonMap("예약 가능 날짜 리스트: ", availableDates));
    }

    @Operation(summary = "해당 디자이너의 선택한 날짜의 예약 가능 시간을 조회합니다.")
    @GetMapping("/available-times")
    public SuccessResponse<Map<String, List<LocalTime>>> getAvailableTimes(@RequestParam @Min(1) Long designerId, @RequestParam LocalDate bookingDate) {
        List<LocalTime> availableTimes = getAvailableTimesUseCase.execute(designerId, bookingDate);
        return SuccessResponse.of(Collections.singletonMap("예약 가능 시간 리스트: ", availableTimes))
    };

    @Operation(summary = "사용자의 다가오는 예약 내역을 조회합니다.")
    @GetMapping("/current")
    public SuccessResponse<Object> getBookings(){
        List<Booking> bookingList = bookingService.getCurrentBookings();

        return SuccessResponse.of(bookingList);
    }

    @Operation(summary = "사용자의 지난 예약 내역을 조회합니다.")
    @GetMapping("/past")
    public SuccessResponse<Object> getPastBookings(){
        List<Booking> bookingList = bookingService.getPastBookings();

        return SuccessResponse.of(bookingList);
    }
}
