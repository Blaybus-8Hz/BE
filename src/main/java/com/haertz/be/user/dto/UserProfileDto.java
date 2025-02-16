package com.haertz.be.user.dto;

import com.haertz.be.auth.entity.User;

public record UserProfileDto(String name, String email){
    public static UserProfileDto from(User user){
        return new UserProfileDto(user.getUserName(), user.getAuthInfo().getEmail());
    }
}
