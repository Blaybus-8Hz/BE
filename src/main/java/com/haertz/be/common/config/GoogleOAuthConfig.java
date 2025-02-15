package com.haertz.be.common.config;

import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;
public class GoogleOAuthConfig {
    // Feign 클라이언트에서 Form-encoded 방식으로 데이터 인코딩 하기 위한 목적
    @Bean
    Encoder formEncoder(){
        return new feign.form.FormEncoder();
    }
}