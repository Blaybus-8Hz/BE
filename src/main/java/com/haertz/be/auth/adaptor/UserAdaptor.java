package com.haertz.be.auth.adaptor;

import com.haertz.be.auth.entity.User;
import com.haertz.be.auth.exception.UserErrorCode;
import com.haertz.be.auth.repository.UserRepository;
import com.haertz.be.common.annotation.Adaptor;
import com.haertz.be.common.exception.base.BaseException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class UserAdaptor {
    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    }

    public User findByEmail(String email) {
        return userRepository.findByAuthInfoEmail(email)
                .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByAuthInfoEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }


}