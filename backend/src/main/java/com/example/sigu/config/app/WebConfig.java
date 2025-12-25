package com.example.sigu.config.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // No usar "*" //TODO: Cambiar para produccion
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true); // Obligatorio para cookies
    }
}
