package com.haertz.be.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MeetingStatus {
    ONLINE("ONLINE"),
    OFFLINE("OFFLINE");

    public final String value;
}
