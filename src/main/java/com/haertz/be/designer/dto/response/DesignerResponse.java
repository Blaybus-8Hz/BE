package com.haertz.be.designer.dto.response;

import com.haertz.be.booking.entity.MeetingType;
import com.haertz.be.designer.entity.District;
import com.haertz.be.designer.entity.Specialty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DesignerResponse {

    private List<Specialty> designerSpecialty;
    private String designerName;
    private String designerShop;
    private Integer designerUntactCost;// 가격
    private Integer designerContactCost;
    private String designerDescription; //디자이너 한줄소개
    private MeetingType meetingType; //대면상담, 비대면 상담 여부
}
