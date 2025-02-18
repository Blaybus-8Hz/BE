package com.haertz.be.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MeetingType {
    REMOTE("REMOTE"),
    FACE_TO_FACE("FACE_TO_FACE");

    public final String value;
}
