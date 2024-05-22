package com.restaurant.vcriate.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Vcriate Restaurant Management",
                version = "1.0",
                contact = @Contact(
                        name = "Vcriate's Team",
                        url = "Vcriate.com",
                        email = "support@Vcriate.one"
                ),
                description = "API for Vcriate - A restaurant management.",
                termsOfService = "Terms of service: By using the Vcriate API, you agree to comply with the terms and conditions outlined in our official documentation."
        ),
        servers = {
                @Server(
                        description = "LOCAL ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "http://Vcriate.com"
                )
        }
)
public class SwaggerConfig {

}
