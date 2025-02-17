package com.haertz.be.googlemeet.exception;

import com.haertz.be.common.exception.base.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GoogleMeetErrorCode implements BaseErrorCode {
    FAILED_TO_CREATE_MEET_LINK(500, "GOOGLE_MEET_500", "구글 미팅 링크생성에 실패하였습니다.");
    private final int httpStatus;
    private final String code;
    private final String message;
}
