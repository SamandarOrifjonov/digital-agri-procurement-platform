package com.agrifood.platform.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Digital Procurement Platform API")
                .version("1.0.0")
                .description("API for agricultural procurement platform - manage opportunities, bids, suppliers, and contracts")
                .contact(new Contact()
                    .name("AgriFood Platform Team")
                    .email("support@agrifood.com"))
                .license(new License()
                    .name("Proprietary")
                    .url("https://agrifood.com/license")));
    }
}
