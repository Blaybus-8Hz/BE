package com.haertz.be.common.exception.base;

public interface BaseErrorCode {
    public String getCode();
    public String getMessage();
    public int getHttpStatus();
}
