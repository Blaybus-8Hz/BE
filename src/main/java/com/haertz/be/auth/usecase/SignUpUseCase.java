package com.haertz.be.auth.usecase;

import com.haertz.be.auth.adaptor.UserAdaptor;
import com.haertz.be.auth.dto.response.AccountTokenDto;
import com.haertz.be.auth.entity.AuthInfo;
import com.haertz.be.auth.entity.LoginType;
import com.haertz.be.auth.entity.User;
import com.haertz.be.auth.exception.UserErrorCode;
import com.haertz.be.auth.service.UserDomainService;
import com.haertz.be.auth.usecase.processor.GenerateAccountTokenProcessor;
import com.haertz.be.auth.usecase.processor.GoogleOauthProcessor;
import com.haertz.be.common.annotation.UseCase;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.common.jwt.dto.UserInfoFromIdToken;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SignUpUseCase {
    private final UserDomainService userDomainService;

    private final UserAdaptor userAdaptor;
    private final GoogleOauthProcessor googleOauthProcessor;
    private final GenerateAccountTokenProcessor generateAccountTokenProcessor;


    public AccountTokenDto execute(String loginType, String idToken, HttpServletResponse response){
        if (loginType.equals("google")) {
            UserInfoFromIdToken userInfo = googleOauthProcessor.getUserInfoByIdToken(idToken);

            if (!userAdaptor.existsByEmail(userInfo.getEmail())) {
                AuthInfo authInfo = AuthInfo.authInfoForSignUp((LoginType.fromValue(loginType)), userInfo.getEmail());
                User user = userDomainService.signUp(userInfo.getName(), authInfo);
                return generateAccountTokenProcessor.createToken(user, response);
            } else throw new BaseException(UserErrorCode.EMAIL_ALREADY_REGISTERED);
        }
        throw new BaseException(UserErrorCode.NOT_SUPPORTED_LOGINTYPE_ERROR);
    }
}