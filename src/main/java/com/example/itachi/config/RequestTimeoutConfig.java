package com.example.itachi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class RequestTimeoutConfig extends WebMvcConfigurationSupport {
    @Override
    protected void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(5000);
        configurer.registerCallableInterceptors(timeoutCallableProcessingInterceptor());
    }

    @Bean
    public TimeoutCallableProcessingInterceptor timeoutCallableProcessingInterceptor(){
        return new TimeoutCallableProcessingInterceptor();
    }
}
