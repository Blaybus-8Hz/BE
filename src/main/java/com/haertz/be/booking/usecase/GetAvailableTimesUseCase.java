package com.haertz.be.booking.usecase;

import com.haertz.be.booking.service.DesignerScheduleDomainService;
import com.haertz.be.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetAvailableTimesUseCase {
    private final DesignerScheduleDomainService designerScheduleDomainService ;

    public List<LocalTime> execute(Long designerId, LocalDate bookingDate) {
        return designerScheduleDomainService.getAvailableTimes(designerId, bookingDate);
    }

}
