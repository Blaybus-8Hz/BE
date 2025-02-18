package com.haertz.be.common.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
BaseEntity 자동 관리 설정 클래스
 */
@EnableJpaAuditing
@Configuration
public class JpaAuditingConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
