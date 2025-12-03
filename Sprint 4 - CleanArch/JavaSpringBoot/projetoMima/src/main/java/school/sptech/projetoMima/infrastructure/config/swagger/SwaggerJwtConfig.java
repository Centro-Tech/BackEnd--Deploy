package school.sptech.projetoMima.infrastructure.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerJwtConfig {

    @Bean
    public OpenAPI openAPIautentica() {
        final String autorizacaoUsada = "bearerAuth";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(autorizacaoUsada))
                .components(new Components()
                        .addSecuritySchemes(autorizacaoUsada,
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ));
    }
}
