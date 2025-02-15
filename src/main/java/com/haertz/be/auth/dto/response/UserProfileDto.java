package com.haertz.be.auth.dto.response;

public record UserProfileDto(String name, String email){
    public static UserProfileDto from(String name, String email){
        return new UserProfileDto(name, email);
    }
}
