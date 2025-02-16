package com.haertz.be.auth.usecase;

import com.haertz.be.auth.dto.response.IdTokenDto;
import com.haertz.be.auth.exception.UserErrorCode;
import com.haertz.be.auth.usecase.processor.GoogleOauthProcessor;
import com.haertz.be.common.annotation.UseCase;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.common.feign.GoogleTokenResponse;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RequestTokenUseCase {
    private final GoogleOauthProcessor googleOauthProcessor;
    public IdTokenDto execute(String loginType, String code) {
        if (loginType.equals("google")) {
            GoogleTokenResponse googleTokenResponse = googleOauthProcessor.getOauthToken(code);
            return IdTokenDto.of(googleTokenResponse.getIdToken());
        }
        throw new BaseException(UserErrorCode.NOT_SUPPORTED_LOGINTYPE_ERROR);
    }
}