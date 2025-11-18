package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import jakarta.annotation.Priority;

@Provider
@ApplicationScoped
@Priority(1)
public class AuthenticationFilter implements ContainerRequestFilter {

    @ConfigProperty(name = "api.security.valid-api-key")
    private String validApiKey;

    private static final String API_KEY_HEADER = "X-API-Key";

    @Override
    public void filter(ContainerRequestContext requestContext) {

        if (validApiKey == null || validApiKey.isBlank()) {
            System.out.println("*************************************************");
            System.out.println(" ERRO CRÍTICO: A CHAVE SECRETA NÃO FOI CARREGADA DO PROPERTIES! ");
            System.out.println("*************************************************");
            requestContext.abortWith(Response.serverError().entity("Configuração de segurança interna falhou.").build());
            return;
        }


        System.out.println("========================================================");
        System.out.println("--- FILTRO DE AUTENTICAÇÃO ATIVADO ---");

        String apiKey = requestContext.getHeaderString(API_KEY_HEADER);

        System.out.println("Chave ESPERADA (do properties): " + validApiKey);
        System.out.println("Chave RECEBIDA (do header)   : " + apiKey);

        if (apiKey == null || !validApiKey.equals(apiKey)) {
            System.out.println("--> RESULTADO: Chaves são DIFERENTES ou NULA. Bloqueando requisição.");
            System.out.println("========================================================");

            GlobalExceptionHandler.ErrorResponse errorResponse =
                    new GlobalExceptionHandler.ErrorResponse("Chave de API inválida ou não fornecida.", null);

            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(errorResponse)
                            .build()
            );
        } else {
            System.out.println("--> RESULTADO: Chaves são IGUAIS. Liberando requisição.");
            System.out.println("========================================================");
        }
    }
}