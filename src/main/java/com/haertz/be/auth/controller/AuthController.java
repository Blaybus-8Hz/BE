package com.haertz.be.auth.controller;


import com.haertz.be.auth.dto.response.AccountTokenDto;
import com.haertz.be.auth.dto.response.IdTokenDto;
import com.haertz.be.auth.usecase.LoginUseCase;
import com.haertz.be.auth.usecase.RequestTokenUseCase;
import com.haertz.be.auth.usecase.SignUpUseCase;
import com.haertz.be.common.response.SuccessResponse;
import com.haertz.be.common.utils.TokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "사용자 인증 API", description = "사용자 인증 관련 API 입니다.")
public class AuthController {
    private final RequestTokenUseCase requestTokenUseCase;
    private final SignUpUseCase signUpUseCase;
    private final LoginUseCase loginUseCase;

    @Operation(summary = "구글 id token을 발급받습니다.")
    @GetMapping("/idtoken")
    public SuccessResponse<Object> getIdToken(@RequestParam("code") String code,
                                              @RequestParam(value = "logintype", defaultValue = "google") String loginType) {
        IdTokenDto idTokenDto = requestTokenUseCase.execute(loginType, code);
        return SuccessResponse.of(idTokenDto);
    }

    @Operation(summary = "구글 id token으로 회원가입합니다.")
    @PostMapping("/signup")
    public SuccessResponse<Object> signUp(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(value = "logintype", defaultValue = "google") String loginType) {
        AccountTokenDto accountTokenDto = signUpUseCase.execute(loginType, TokenUtils.resolveToken(request), response);
        return SuccessResponse.of(accountTokenDto);
    }

    @Operation(summary = "구글 id token으로 로그인합니다.")
    @PostMapping("/login")
    public SuccessResponse<Object> login(
            HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "logintype", defaultValue = "google") String loginType) {
        AccountTokenDto accountTokenDto = loginUseCase.execute(loginType, TokenUtils.resolveToken(request), response);
        return SuccessResponse.of(accountTokenDto);
    }


}