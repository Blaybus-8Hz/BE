package com.haertz.be.payment.entity;

import com.haertz.be.booking.entity.Booking;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="payment_id") //DB에서 자동 생성되는 PK 값
    private Long paymentId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="booking_id",referencedColumnName = "booking_id")
    private Booking booking;

    @Column(nullable = false,name="user_id")
    private Long userId;  //카카오페이시 이걸 partner_user_id로 사용해도 됨?

    @Column(nullable = false,name="payment_date")
    private Date paymentDate;

    @Column(nullable = false,name="total_amount")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name="payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name="payment_status")
    private PaymentStatus paymentStatus=PaymentStatus.PENDING;

    @Column(name="partner_order_id") //결제 건을 식별하는 외부 주문 번호 역할- >디자이너스케줄테이블의 아이디 사용
    private String partnerOrderId;

    @Column(name="payment_transaction") //카카오페이의 tid
    private String paymentTransaction;
}
