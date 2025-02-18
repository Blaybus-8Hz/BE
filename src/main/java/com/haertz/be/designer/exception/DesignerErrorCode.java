package com.haertz.be.designer.exception;

import com.haertz.be.common.exception.base.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DesignerErrorCode implements BaseErrorCode{
    DESIGNER_NOT_FOUND(404, "DESIGNER_404","디자이너를 찾을 수 없습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}

