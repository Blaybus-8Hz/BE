package com.haertz.be.auth.usecase.processor;

import com.haertz.be.common.annotation.Processor;
import com.haertz.be.common.feign.GoogleOAuthApi;
import com.haertz.be.common.feign.GoogleTokenResponse;
import com.haertz.be.common.feign.oidc.GoogleOIDCApi;
import com.haertz.be.common.feign.oidc.OIDCPublicKeysResponse;
import com.haertz.be.common.jwt.OAuthOIDCProcessor;
import com.haertz.be.common.jwt.dto.OIDCDecodePayload;
import com.haertz.be.common.jwt.dto.UserInfoFromIdToken;
import com.haertz.be.common.jwt.properties.OauthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.haertz.be.common.constant.StaticValue.GOOGLE_GRANT_TYPE;
import static com.haertz.be.common.constant.StaticValue.GOOGLE_ISS_URL;


@Slf4j
@Processor
@RequiredArgsConstructor
public class GoogleOauthProcessor {
    private final OAuthOIDCProcessor oAuthOIDCProcessor;
    private final GoogleOIDCApi googleOIDCApi;
    private final GoogleOAuthApi googleOAuthApi;
    private final OauthProperties oauthProperties;

    public GoogleTokenResponse getOauthToken(String code) {
        return googleOAuthApi.getGoogleToken(
                code,
                oauthProperties.getGoogleClientId(),
                oauthProperties.getGoogleClientSecret(),
                oauthProperties.getGoogleRedirectUri(),
                GOOGLE_GRANT_TYPE);
    }

    // 구글 공개키 리스트 가져온 후, idToken을 디코딩하고 검증하고 idToken 정보를 반환한다
    public OIDCDecodePayload getOIDCDecodePayload(String idtoken) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = googleOIDCApi.getGoogleOIDCOpenKeys();
        return oAuthOIDCProcessor.getPayloadFromIdToken(
                idtoken,
                GOOGLE_ISS_URL, // iss
                oauthProperties.getGoogleClientId(), // aud
                oidcPublicKeysResponse);
    }

    // idToken에서 메일과 이름을 추출하여 반환한다
    public UserInfoFromIdToken getUserInfoByIdToken(String idToken) {
        OIDCDecodePayload oidcDecodePayload = getOIDCDecodePayload(idToken);
        return UserInfoFromIdToken.builder()
                .name(oidcDecodePayload.getName())
                .email(oidcDecodePayload.getEmail())
                .build();
    }

}