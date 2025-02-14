package com.haertz.be.common.config.security;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

//  현재 인증 정보를 가져온 후 사용자 id 추출
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {
    public static Long getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof Long){
            return (Long) authentication.getPrincipal();
        }
        else return 0L;
    }
}