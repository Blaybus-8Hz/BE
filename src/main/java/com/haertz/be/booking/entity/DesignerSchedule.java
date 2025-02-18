package com.haertz.be.booking.entity;

import com.haertz.be.common.entity.BaseTimeEntity;
import com.haertz.be.payment.entity.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


/*
디자이너 별 예약 확정 시간 엔티티
*/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "designer_schedule", uniqueConstraints = @UniqueConstraint(columnNames = {"designer_id", "booking_date", "booking_time"}))
public class DesignerSchedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long designerScheduleId;

    @NotNull
    @Column(nullable = false)
    private LocalDate bookingDate;

    @NotNull
    @Column(nullable = false)
    private LocalTime bookingTime;

    @Column(nullable = false)
    private Long designerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private boolean isTemporary;

    @NotNull
    @Column(nullable = false)
    private Long userId;

    public void markAsCompleted(){
        isTemporary = false;
        paymentStatus = PaymentStatus.COMPLETED;
    }
}
