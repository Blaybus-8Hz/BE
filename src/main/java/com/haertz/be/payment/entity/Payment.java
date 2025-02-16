package com.haertz.be.payment.entity;

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

    /* Reservation entity생성후 변경
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="reservation_id",referencedColumnName = "reservation_id")
    private Reservation reservation
    */
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

    @Column(name="partner_order_id") //외부 결제 서비스(카카오페이)에서 사용되는 주문 ID.
    private String partnerOrderId;

    @Column(name="payment_transaction") //카카오페이의 tid
    private String paymentTransaction;
}
