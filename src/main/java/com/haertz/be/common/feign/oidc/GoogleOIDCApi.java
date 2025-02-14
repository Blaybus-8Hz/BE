package com.haertz.be.common.feign.oidc;

import com.haertz.be.common.config.GoogleOAuthConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "GoogleOIDCApi",
        url = "https://www.googleapis.com/oauth2",
        configuration = GoogleOAuthConfig.class)
public interface GoogleOIDCApi {
    @Cacheable(cacheNames = "GoogleOICD", cacheManager = "oidcCacheManager")
    @GetMapping("/v3/certs")
    OIDCPublicKeysResponse getGoogleOIDCOpenKeys();
}