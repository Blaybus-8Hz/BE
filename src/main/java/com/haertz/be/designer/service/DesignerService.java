package com.haertz.be.designer.service;

import com.haertz.be.auth.exception.UserErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.designer.dto.DesignerResponse;
import com.haertz.be.designer.entity.Designer;
import com.haertz.be.designer.repository.DesignerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DesignerService {



    private ModelMapper modelMapper;

    private final DesignerRepository designerRepository;

    public DesignerResponse getDesignerResponse(Long designerId) {
        Designer designer = designerRepository.findByDesignerId(designerId);
        return modelMapper.map(designer, DesignerResponse.class);
    }
}
