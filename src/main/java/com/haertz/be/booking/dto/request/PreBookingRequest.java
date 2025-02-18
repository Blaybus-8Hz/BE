package com.haertz.be.booking.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record PreBookingRequest(
        @NotNull(message = "디자이너 ID는 필수 값입니다.") Long designerId,
        @NotNull(message = "날짜는 필수 값입니다.") LocalDate bookingDate,
        @NotNull(message = "시간 필수 값입니다.")
        @DateTimeFormat(pattern = "HH:mm:ss")
        LocalTime bookingTime
) {
}
