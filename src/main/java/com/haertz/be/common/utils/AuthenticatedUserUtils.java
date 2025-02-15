package com.haertz.be.common.utils;

import com.haertz.be.auth.adaptor.UserAdaptor;
import com.haertz.be.auth.entity.User;
import com.haertz.be.common.config.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticatedUserUtils {
    private final UserAdaptor userAdaptor;

    public Long getCurrentUserId(){
        return SecurityUtils.getCurrentUserId();
    }
    public User getCurrentUser(){
        return userAdaptor.findById(getCurrentUserId());
    }
}
