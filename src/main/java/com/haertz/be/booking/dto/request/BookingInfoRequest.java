package com.haertz.be.booking.dto.request;

import com.haertz.be.booking.entity.MeetingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookingInfoRequest(
        @NotNull(message = "디자이너 ID는 필수 값입니다.") Long designerId,
        Long designerScheduleId,
        LocalDate bookingDate,

        @DateTimeFormat(pattern = "HH:mm:ss")
        LocalTime bookingTime,
        String requestDetails,
        MeetingType meetingType
) {
}
