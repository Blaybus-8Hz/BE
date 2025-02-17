package com.haertz.be.booking.entity;

import com.haertz.be.auth.entity.User;
import com.haertz.be.common.entity.BaseTimeEntity;
import com.haertz.be.designer.entity.Designer;
import com.haertz.be.payment.entity.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
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
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.STRING)
    private MeetingStatus meetingStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String requestDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Designer designer;

}
