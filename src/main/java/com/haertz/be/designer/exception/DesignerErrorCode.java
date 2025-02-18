package com.haertz.be.designer.exception;

import com.haertz.be.common.exception.base.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DesignerErrorCode implements BaseErrorCode{
    DESIGNER_UNSUPPORTED_MEETING(405, "DESIGNER_400", "해당 디자이너에 해당 미팅 방식을 지원하지 않습니다."),
    DESIGNER_NOT_FOUND(404, "DESIGNER_404","디자이너를 찾을 수 없습니다."),
    INVALID_MEETING_MODE(400, "DESIGNER_400_INVALID", "잘못된 미팅 방식입니다.");;

    private final int httpStatus;
    private final String code;
    private final String message;
}

