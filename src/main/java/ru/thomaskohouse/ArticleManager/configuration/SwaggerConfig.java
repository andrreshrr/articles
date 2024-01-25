package ru.thomaskohouse.ArticleManager.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Articles",
                description = "Article management system", version = "1.0.0",
                contact = @Contact(
                        name = "Andrey Oskin",
                        email = "rreshrr@gmail.com",
                        url = "localhost.."
                )
        )
)
public class SwaggerConfig {
}
