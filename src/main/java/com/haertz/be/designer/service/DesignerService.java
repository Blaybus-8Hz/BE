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

    public List<Designer> getDesignerResponseByCategories(Specialty categories) {
        return designerRepository.findByDesignerSpecialty(categories);
    }

    public List<Designer> getAll() {
        return designerRepository.findAll();
    }

    public List<Designer> getListByDistrict(District district){
        return designerRepository.findByDesignerDistrict(district);
    }

    public List<Designer> getListByMeetingMode(MeetingMode meetingMode){
        return designerRepository.findByMeetingMode(meetingMode);
    }


    public List<Designer> filterDesigners(List<District> districts, List<MeetingMode> meetingModes, List<Specialty> categories) {
        return designerRepository.findAll().stream()
                .filter(designer -> (districts == null || districts.isEmpty() || districts.contains(designer.getDesignerDistrict())))
                .filter(designer -> (meetingModes == null || meetingModes.isEmpty() || meetingModes.contains(designer.getMeetingMode())))
                .filter(designer -> (categories == null || categories.isEmpty() ||
                        categories.stream().anyMatch(category -> category.equals(designer.getDesignerSpecialty())))) // OR 조건 적용
                .collect(Collectors.toList());
    }

}
