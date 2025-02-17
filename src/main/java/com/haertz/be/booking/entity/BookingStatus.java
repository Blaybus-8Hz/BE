package com.haertz.be.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookingStatus {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    CANCELED("CANCELED");

    private final String value;
}
