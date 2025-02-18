package com.haertz.be.designer.entity;

import com.haertz.be.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "meeting_mode", nullable = false)
    private MeetingMode meetingMode;

    @Column(name = "designer_shop")
    private String designerShop; // 매장주소

    @Enumerated(EnumType.STRING)
    @Column(name = "designer_district")
    private District designerDistrict;

    @Enumerated(EnumType.STRING)
    @Column(name = "designer_specialty")
    private Specialty designerSpecialty;

    @Column(name = "designer_contact_cost")
    private Integer designerContactCost; // 대면 가격

    @Column(name = "designer_untact_cost")
    private Integer designerUntactCost; // 비대면 가격


    @Column(name = "designer_description")
    private String designerDescription; // 디자이너 한줄소개



}
