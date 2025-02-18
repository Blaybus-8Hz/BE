package com.haertz.be.user.usecase;

import com.haertz.be.auth.entity.User;
import com.haertz.be.common.annotation.UseCase;
import com.haertz.be.common.utils.AuthenticatedUserUtils;
import com.haertz.be.user.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@RequiredArgsConstructor
public class GetUserProfileUseCase {
    private final AuthenticatedUserUtils userUtils;
    public UserProfileDto execute(){
        User user = userUtils.getCurrentUser();
        return UserProfileDto.from(user);
    }


}
