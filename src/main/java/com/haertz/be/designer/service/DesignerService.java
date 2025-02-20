package com.haertz.be.designer.service;

import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.designer.dto.response.DesignerResponse;
import com.haertz.be.designer.entity.Designer;
import com.haertz.be.designer.entity.District;
import com.haertz.be.designer.entity.MeetingMode;
import com.haertz.be.designer.entity.Specialty;
import com.haertz.be.designer.exception.DesignerErrorCode;
import com.haertz.be.designer.repository.DesignerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.pqc.crypto.DigestingStateAwareMessageSigner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DesignerService {

    @Autowired
    private ModelMapper modelMapper;

    private final DesignerRepository designerRepository;

    public DesignerResponse getDesignerResponse(Long designerId) {
        Designer designer = designerRepository.findByDesignerId(designerId);
        return modelMapper.map(designer, DesignerResponse.class);
    }

    public Page<Designer> getAllDesigners(@NonNull Pageable pageable) {
        return designerRepository.findAll(pageable);
    }

    public Page<Designer> getFilteredDesigners(MeetingMode meetingMode, District district, List<Specialty> specialties, @NonNull Pageable pageable) {
        if (meetingMode == MeetingMode.BOTH) {
            throw new BaseException(DesignerErrorCode.INVALID_MEETING_MODE);
        }

        return designerRepository.findFilteredDesigners(meetingMode, district, specialties, pageable);
    }



}
