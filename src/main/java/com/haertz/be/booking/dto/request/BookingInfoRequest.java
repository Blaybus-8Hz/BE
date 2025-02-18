package com.haertz.be.booking.dto.request;

import com.haertz.be.booking.entity.MeetingType;
import jakarta.validation.constraints.NotNull;

public record BookingInfoRequest(
        @NotNull(message = "designerSchedule ID는 필수 값입니다.")  Long designerScheduleId,
        String requestDetails,
        MeetingType meetingType
) {
}
