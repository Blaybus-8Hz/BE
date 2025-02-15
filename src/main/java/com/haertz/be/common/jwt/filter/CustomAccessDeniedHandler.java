package com.haertz.be.common.jwt.filter;

import com.haertz.be.common.exception.GlobalErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.common.jwt.FilterExceptionProcessor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// Spring Security에서 사용자가 접근 권한이 없을 때 발생하는 AccessDeniedException을 처리하기 위한 커스텀 핸들러
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final FilterExceptionProcessor filterExceptionProcessor;
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {
        filterExceptionProcessor.excute(response, new BaseException(GlobalErrorCode.ACCESS_DENIED_REQUEST));
    }

}
