package com.haertz.be.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haertz.be.common.annotation.Processor;
import com.haertz.be.common.exception.base.BaseException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Spring Security 필터에서 발생한 예외
@Processor
@RequiredArgsConstructor
public class FilterExceptionProcessor {

    private final ObjectMapper objectMapper;

    public void excute(HttpServletResponse response, BaseException e) throws IOException {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("isSuccess", false);
        errorResponse.put("message", e.getErrorCode().getMessage());
        errorResponse.put("code", e.getErrorCode());

        response.setCharacterEncoding("UTF-8");
        response.setStatus(e.getErrorCode().getHttpStatus());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse)); // json 문자열이 응답 본문에 작성된다.
    }
}
