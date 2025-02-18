package com.haertz.be.booking.entity;

import com.haertz.be.booking.dto.request.BookingInfoRequest;
import com.haertz.be.common.entity.BaseTimeEntity;
import com.haertz.be.payment.entity.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "booking")
public class Booking extends BaseTimeEntity {

    @Id
    @Column(name = "booking_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @NotNull
    @Column(nullable = false)
    private LocalDate bookingDate;

    @NotNull
    @Column(nullable = false)
    private LocalTime bookingTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus; // 예약 확정, 대기중, 취소

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // 결제 완료, 대기, 환불

    @Enumerated(EnumType.STRING)
    private MeetingType meetingType; // 컨설팅 방식 결정

    @Column(columnDefinition = "TEXT", nullable = true)
    private String requestDetails;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long designerId;


}
