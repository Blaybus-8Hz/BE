package com.haertz.be.common.jwt.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration")
public class OauthProperties {
    private OauthSecret google;

    public String getGoogleClientId() {
        return google.getClientId();
    }

    public String getGoogleClientSecret() {
        return google.getClientSecret();
    }

    public String getGoogleClientName() {
        return google.getClientName();
    }

    public String getGoogleRedirectUri() {
        return google.getRedirectUri();
    }

    public String getGoogleScope(){
        return google.getScope();
    }

    @Getter
    @Setter
    public static class OauthSecret {
        private String scope;
        private String clientId;
        private String clientSecret;
        private String clientName;
        private String redirectUri;
    }

}