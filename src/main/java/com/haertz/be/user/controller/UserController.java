package com.haertz.be.user.controller;

import com.haertz.be.common.response.SuccessResponse;
import com.haertz.be.user.usecase.GetUserProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "유저 정보 API", description = "사용자의 정보를 관리하는 API입니다.")

public class UserController {
    private final GetUserProfileUseCase getUserProfileUseCase;

    @Operation(summary = "사용자 profile를 제공합니다.")
    @GetMapping("/profile")
    public SuccessResponse<Object> getUserProfile(){
        return SuccessResponse.of(getUserProfileUseCase.execute());
    }

}
