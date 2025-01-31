package com.ucb.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Aplica CORS a todas las rutas
                        .allowedOrigins("http://localhost:5173") // Permite el frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Habilita todos los métodos
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
