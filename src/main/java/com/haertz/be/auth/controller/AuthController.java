package com.haertz.be.auth.controller;


import com.haertz.be.auth.dto.response.IdTokenDto;
import com.haertz.be.auth.usecase.*;
import com.haertz.be.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "사용자 인증 API", description = "사용자 인증 관련 API 입니다.")
public class AuthController {
    private final RequestTokenUseCase requestTokenUseCase;

    @Operation(summary = "구글 id token을 발급받습니다.")
    @GetMapping("/idtoken")
    public SuccessResponse<Object> getIdToken(@RequestParam("code") String code,
                                              @RequestParam(value = "logintype", defaultValue = "google") String loginType) {
        IdTokenDto idTokenDto = requestTokenUseCase.execute(loginType, code);
        return SuccessResponse.of(idTokenDto);
    }
}