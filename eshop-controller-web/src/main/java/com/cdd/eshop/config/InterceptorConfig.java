package com.cdd.eshop.config;

import com.cdd.eshop.filter.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(LoginHandlerInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public LoginHandlerInterceptor LoginHandlerInterceptor() {
        return new LoginHandlerInterceptor();
    }
}
