package com.haertz.be.designer.service;

import com.haertz.be.designer.dto.response.DesignerResponse;
import com.haertz.be.designer.entity.Designer;
import com.haertz.be.designer.entity.District;
import com.haertz.be.designer.entity.MeetingMode;
import com.haertz.be.designer.entity.Specialty;
import com.haertz.be.designer.repository.DesignerRepository;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.pqc.crypto.DigestingStateAwareMessageSigner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Designer> getDesignerResponseByCategories(Specialty categories) {
        return designerRepository.findByDesignerSpecialty(categories);
    }

    public List<Designer> getAll(){
        return designerRepository.findAll();
    }

    public List<Designer> getListByDistrict(District district){
        return designerRepository.findByDesignerDistrict(district);
    }

    public List<Designer> getListByMeetingMode(MeetingMode meetingMode){
        return designerRepository.findByMeetingMode(meetingMode);
    }

}
