package com.haertz.be.designer.dto;

import com.haertz.be.booking.entity.MeetingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DesignerResponse {

    private String designerSpecialty;
    private String designerName;
    private String designerDistrict;
    private Integer designerUntactCost;
    private Integer designerContactCost;
    private String designerDescription; //디자이너 한줄소개
    private MeetingStatus meetingStatus; //대면상담, 비대면 상담 여부
}
