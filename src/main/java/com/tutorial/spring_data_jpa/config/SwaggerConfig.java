package com.tutorial.spring_data_jpa.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Data JPA Tutorial API")
                        .description("Example API for managing end-points")
                        .version("v1.0.0"));
    }

    // Swagger UI URL: http://localhost:8080/swagger-ui/index.html
}
