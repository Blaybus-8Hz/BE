package com.haertz.be.designer.adaptor;

import com.haertz.be.common.annotation.Adaptor;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.designer.entity.Designer;
import com.haertz.be.designer.exception.DesignerErrorCode;
import com.haertz.be.designer.repository.DesignerRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class DesignerAdaptor {
    private final DesignerRepository designerRepository;

    public Designer findByDesignerId(Long designerId){
        return designerRepository.findById(designerId)
                .orElseThrow(()-> new BaseException(DesignerErrorCode.DESIGNER_NOT_FOUND));
    }
}
