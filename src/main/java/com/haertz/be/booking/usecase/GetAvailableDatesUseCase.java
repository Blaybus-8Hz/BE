package com.haertz.be.booking.usecase;

import com.haertz.be.booking.service.DesignerScheduleDomainService;
import com.haertz.be.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetAvailableDatesUseCase {
    private final DesignerScheduleDomainService designerScheduleDomainService ;

    public List<LocalDate> execute(Long designerId){
        return designerScheduleDomainService.getAvailableDates(designerId);
    }
}
