package com.haertz.be.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import com.haertz.be.common.config.GoogleOAuthConfig;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 구글로부터, 액세스/리프레쉬/Id/scope를 받아옵니다.
@FeignClient(
        name = "GoogleOAuth",
        url = "https://oauth2.googleapis.com",
        configuration = GoogleOAuthConfig.class )
public interface GoogleOAuthApi {
    @PostMapping(
            value = "/token",
            produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    GoogleTokenResponse getGoogleToken(
            @RequestParam("code") String code,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("grant_type") String grantType
    );
}