package com.haertz.be.auth.entity;

import com.haertz.be.booking.entity.Booking;
import com.haertz.be.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = {"login_type", "email"}))
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String userName;

    @Embedded
    @NotNull
    private AuthInfo authInfo;


}
