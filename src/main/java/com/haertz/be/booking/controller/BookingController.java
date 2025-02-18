package com.haertz.be.booking.controller;

import com.haertz.be.booking.dto.request.BookingInfoRequest;
import com.haertz.be.booking.dto.response.BookingResponse;
import com.haertz.be.booking.service.BookingDomainService;
import com.haertz.be.booking.usecase.BookUseCase;
import com.haertz.be.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "컨설팅 예약 API", description = "컨설팅 예약 관련 API 입니다.")
@RequestMapping("/api/booking")
public class BookingController {
    private final BookUseCase bookUseCase;

    @Operation(summary = "헤어 컨설팅 일정 예약을 요청합니다.")
    @PostMapping
    public SuccessResponse<BookingResponse> book(@RequestBody @Valid BookingInfoRequest bookingInfoRequest){
        BookingResponse bookingResponse = bookUseCase.execute(bookingInfoRequest);
        return SuccessResponse.of(bookingResponse);
    }
}
