package com.tasks.bnn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("PUT", "DELETE", "POST", "GET", "PATCH", "PUT", "OPTIONS")
                .allowedHeaders("Content-Type", "Token", "Access-Control-Request-Method",
                        "Access-Control-Request-Headers", "Origin", "Access-Control-Allow-Origin",
                        "Authorization")
                .allowCredentials(true).maxAge(3600L);
    }
}
