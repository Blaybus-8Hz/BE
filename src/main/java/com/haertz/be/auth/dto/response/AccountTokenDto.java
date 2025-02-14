package com.haertz.be.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
public class AccountTokenDto {
    @JsonInclude(NON_NULL)
    private String accessToken;

    private Boolean isRegistered;

    public static AccountTokenDto of(String accessToken) {
        return AccountTokenDto.builder()
                .accessToken(accessToken)
                .isRegistered(true)
                .build();
    }

    public static AccountTokenDto notRegistered() {
        return AccountTokenDto.builder()
                .accessToken(null)
                .isRegistered(false)
                .build();
    }

}
