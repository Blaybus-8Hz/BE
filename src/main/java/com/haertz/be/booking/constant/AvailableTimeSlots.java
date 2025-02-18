package com.haertz.be.booking.constant;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AvailableTimeSlots {
    public static final List<LocalTime> TIME_SLOTS;

    static {
        List<LocalTime> slots = new ArrayList<>();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(19, 30);

        while (startTime.isBefore(endTime.plusMinutes(1))) {
            slots.add(startTime);
            startTime = startTime.plusMinutes(30);

        }

        TIME_SLOTS = Collections.unmodifiableList(slots);
    }
}
