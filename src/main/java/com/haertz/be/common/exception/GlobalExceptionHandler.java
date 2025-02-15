package com.haertz.be.common.exception;

import com.haertz.be.auth.controller.AuthController;
import com.haertz.be.common.exception.base.BaseErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.common.response.ErrorResponse;
import com.haertz.be.user.controller.UserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ SQLIntegrityConstraintViolationException.class, DataIntegrityViolationException.class })
    public ResponseEntity<ErrorResponse> handleSQLIntegrityConstraintViolationException(Exception ex) {
        log.error("중복된 값으로 인해 예외가 발생했습니다: {}", ex.getMessage(), ex);
        return makeErrorResponse(GlobalErrorCode.DUPLICATED_KEY);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request)
    {
        // 첫 번째 유효성 검사 오류 가져오기
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        ErrorResponse errorResponse = ErrorResponse.fromErrorCodeWithCustomMessage(GlobalErrorCode.INVALID_REQUEST_CONTENT, errorMessage);

        return ResponseEntity.status(GlobalErrorCode.INVALID_REQUEST_CONTENT.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(BaseException e) {
        StackTraceElement element = e.getStackTrace()[0];
        String className = element.getClassName();
        String methodName = element.getMethodName();

        // 로그에 예외가 발생한 클래스와 메서드명 추가
        log.error("Exception occurred in {}.{}: {}", className, methodName, e.getErrorCode().getMessage());
        BaseErrorCode errorCode = e.getErrorCode();
        return makeErrorResponse(errorCode);
    }

    private ResponseEntity<ErrorResponse> makeErrorResponse(BaseErrorCode errorCode) {
        ErrorResponse res = ErrorResponse.fromErrorCode(errorCode);

        return ResponseEntity.status(errorCode.getHttpStatus()).body(res);
    }

}
