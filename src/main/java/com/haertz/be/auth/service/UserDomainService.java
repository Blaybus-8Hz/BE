package com.haertz.be.auth.service;

import com.haertz.be.auth.adaptor.UserAdaptor;
import com.haertz.be.auth.entity.AccountStatus;
import com.haertz.be.auth.entity.AuthInfo;
import com.haertz.be.auth.entity.LoginType;
import com.haertz.be.auth.entity.User;
import com.haertz.be.auth.exception.UserErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // 사용자를 관리하는 클래스
public class UserDomainService {
    private final UserAdaptor userAdaptor;

    @Transactional(readOnly = true)
    public User login(LoginType loginType, String email) {
        User user = userAdaptor.findByEmail(email);
        validateUser(user, loginType);
        return user;
    }

    private void validateUser(User user, LoginType loginType) {
        if (!user.getAuthInfo().getLoginType().equals(loginType)) {
            throw new BaseException(UserErrorCode.EMAIL_ALREADY_REGISTERED);
        }
    }

    @Transactional
    public User signUp(String name, AuthInfo authInfo) {
        User user = User.builder()
                .userName(name)
                .authInfo(authInfo)
                .build();
        return userAdaptor.save(user);
    }

    
}