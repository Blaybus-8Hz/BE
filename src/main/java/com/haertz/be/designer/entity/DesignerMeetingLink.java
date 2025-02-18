package com.haertz.be.designer.entity;

import com.haertz.be.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "designer_meeting_link", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"designer_id", "start_time"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DesignerMeetingLink extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 해당 미팅 링크가 어떤 디자이너에 속하는지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id", nullable = false)
    private Designer designer;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    // 미팅 종료 시간 (예: 11:00)
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    // 해당 시간 슬롯에 할당된 구글 미팅 링크 (대면 전용 디자이너는 null)
    @Column(name = "google_meeting_link", unique = true)
    private String googleMeetingLink;
}
