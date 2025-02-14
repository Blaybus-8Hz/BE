package com.haertz.be.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountStatus {
    MEMBER("MEMBER"),
    DELETE("DELETED");

    private final String value;
}
