package com.haertz.be.designer.usecase;

import com.haertz.be.common.annotation.UseCase;
import com.haertz.be.designer.entity.Designer;
import com.haertz.be.designer.service.DesignerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@UseCase
@RequiredArgsConstructor
public class GetAllDesignersUseCase {
    private final DesignerService designerService;

    public Page<Designer> execute(@NonNull Pageable pageable) {
        return designerService.getAllDesigners(pageable);
    }

}
