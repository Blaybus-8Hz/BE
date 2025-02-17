package com.haertz.be.booking.entity;

import com.haertz.be.common.entity.BaseTimeEntity;
import com.haertz.be.designer.entity.Designer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


// 디자이너 별 예약 확정 시간 엔티티

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "designer_booking", uniqueConstraints = @UniqueConstraint(columnNames = {"designer_id, booking_date, booking_time"}))
public class DesignerBookings extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long designerBookingsId;

    @NotNull
    @Column(nullable = false)
    private LocalDate bookingDate;

    @NotNull
    @Column(nullable = false)
    private LocalTime bookingTime;

    @Column(nullable = false)
    private Long designerId;

}
