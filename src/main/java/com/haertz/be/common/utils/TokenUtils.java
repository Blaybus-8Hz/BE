package com.haertz.be.common.utils;

import com.haertz.be.common.exception.GlobalErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import jakarta.servlet.http.HttpServletRequest;

public class TokenUtils {
    public static String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // "Bearer "
        } else throw new BaseException(GlobalErrorCode.HEADER_VALUE_NOT_FOUND);
    }

}