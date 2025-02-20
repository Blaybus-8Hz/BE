package com.haertz.be.designer.usecase;

import com.haertz.be.common.annotation.UseCase;
import com.haertz.be.designer.entity.Designer;
import com.haertz.be.designer.entity.District;
import com.haertz.be.designer.entity.MeetingMode;
import com.haertz.be.designer.entity.Specialty;
import com.haertz.be.designer.service.DesignerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@UseCase
@RequiredArgsConstructor
public class GetFilteredDesignersUseCase {

    private final DesignerService designerService;

    public Page<Designer> execute(MeetingMode meetingMode, District district, Specialty specialty, @NonNull Pageable pageable) {
        return designerService.getFilteredDesigners(meetingMode, district, specialty, pageable);
    }
}
