package com.haertz.be.designer.entity;

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
}
