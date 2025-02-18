package com.haertz.be.designer.entity;

import com.haertz.be.booking.entity.MeetingType;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.designer.exception.DesignerErrorCode;

import java.util.Optional;

public enum MeetingMode {
    REMOTE,        // 비대면만 가능한 경우
    FACE_TO_FACE,  // 대면만 가능한 경우
    BOTH;          // 대면, 비대면 모두 가능한 경우

    public Optional<MeetingType> toMeetingType() {
        if (this == BOTH) {
            return Optional.empty();
        }
        return switch (this) {
            case REMOTE -> Optional.of(MeetingType.REMOTE);
            case FACE_TO_FACE -> Optional.of(MeetingType.FACE_TO_FACE);
            default -> throw new BaseException(DesignerErrorCode.INVALID_MEETING_MODE);
        };
    }
}

