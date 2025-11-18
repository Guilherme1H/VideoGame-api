package org.acme;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.acme.GlobalExceptionHandler.ErrorResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Provider
@Priority(1)
public class SecurityFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @ConfigProperty(name = "api.security.valid-api-key")
    String validApiKey;

    @ConfigProperty(name = "api.ratelimit.requests")
    int maxRequests;

    @ConfigProperty(name = "api.ratelimit.duration.minutes")
    int durationMinutes;

    private final LoadingCache<String, Integer> requestCounts;

    public SecurityFilter() {
        this.requestCounts = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    public Integer load(String key) { return 0; }
                });
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String method = requestContext.getMethod();

        if (method.equals("POST") || method.equals("PUT") || method.equals("DELETE")) {
            String apiKey = requestContext.getHeaderString("X-API-Key");
            if (apiKey == null || !apiKey.equals(validApiKey)) {
                ErrorResponse error = new ErrorResponse("Acesso não autorizado.", Map.of("reason", "Chave de API inválida ou não fornecida."));
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(error).build());
                return;
            }

            try {
                int requests = requestCounts.get(apiKey);
                if (requests >= maxRequests) {
                    ErrorResponse error = new ErrorResponse("Limite de requisições excedido.", Map.of("details", "Tente novamente mais tarde."));
                    requestContext.abortWith(
                            Response.status(Response.Status.TOO_MANY_REQUESTS)
                                    .entity(error)
                                    .header("X-RateLimit-Limit", maxRequests)
                                    .header("X-RateLimit-Remaining", 0)
                                    .build()
                    );
                    return;
                }
                requestCounts.put(apiKey, requests + 1);

            } catch (ExecutionException e) {
                ErrorResponse error = new ErrorResponse("Erro interno no sistema.", Map.of("cause", "Falha ao processar o limite de requisições."));
                requestContext.abortWith(Response.serverError().entity(error).build());
            }
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String method = requestContext.getMethod();
        if (method.equals("POST") || method.equals("PUT") || method.equals("DELETE")) {
            String apiKey = requestContext.getHeaderString("X-API-Key");
            if (apiKey != null) {
                int requests = requestCounts.getUnchecked(apiKey);
                int remaining = Math.max(0, maxRequests - requests);
                responseContext.getHeaders().add("X-RateLimit-Limit", maxRequests);
                responseContext.getHeaders().add("X-RateLimit-Remaining", remaining);
            }
        }
    }
}