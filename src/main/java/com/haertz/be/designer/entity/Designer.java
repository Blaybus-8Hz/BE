package com.haertz.be.designer.entity;

import com.haertz.be.booking.entity.MeetingStatus;
import com.haertz.be.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "designer")
public class Designer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "designer_id")
    private Long designerId;

    @Column(name = "designer_name")
    private String designerName;

    @Column(name = "designer_shop")
    private String designerShop;

    @Column(name = "designer_district")
    private String designerDistrict;

    @Column(name = "designer_specialty")
    private String designerSpecialty;

    @Column(name = "designer_contact_cost")
    private Integer designerContactCost;

    @Column(name = "designer_untact_cost")
    private Integer designerUntactCost;

    @Enumerated(EnumType.STRING)
    private MeetingStatus meetingStatus; //대면상담, 비대면 상담 여부

    @Column(name = "designer_description")
    private String designerDescription; //디자이너 한줄소개

    @Column(name = "designer_account")
    private String designerAccount;
}
