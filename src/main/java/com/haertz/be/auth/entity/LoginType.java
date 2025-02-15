package com.haertz.be.auth.entity;

import com.haertz.be.auth.exception.UserErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginType {
    GOOGLE("google");

    private final String value;

    public static LoginType fromValue(String value){
        for (LoginType type : LoginType.values()){
            if (type.value.equalsIgnoreCase(value)){
                return type;
            }
        }
        throw new BaseException(UserErrorCode.NOT_SUPPORTED_LOGINTYPE_ERROR);
    }
}
