package org.acme;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Contact; // Import necessário
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title = "API de Videogames",
                description = "Uma API RESTful para gerenciar informações sobre videogames, desenvolvedoras e plataformas.",
                version = "1.0.0",
                contact = @Contact(
                name = "Guilherme Henrique",
                email = "itzguihxn@gmail.com"
                )
        )
)
@SecurityScheme(
        securitySchemeName = "apiKeyAuth",
        type = SecuritySchemeType.APIKEY,
        description = "Chave de API necessária para operações de escrita (POST, PUT, DELETE).",
        apiKeyName = "X-API-Key",
        in = SecuritySchemeIn.HEADER
)
public class GameApiApplication extends Application {
}