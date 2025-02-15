package com.haertz.be.common.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserInfoFromIdToken {
    private String name;
    private String email;
}
