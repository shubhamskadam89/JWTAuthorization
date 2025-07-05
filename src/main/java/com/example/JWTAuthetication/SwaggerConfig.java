package com.example.JWTAuthetication;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Emergency Response System API")
                        .description("🚑🚒👮 API documentation for ambulance, police, fire unit dispatch coordination")
                        .version("v1.0.0"));
    }
}
