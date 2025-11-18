package org.acme;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Provider
@Priority(2)
public class IdempotencyFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private final Cache<String, Response> responseCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();

    private static final String IDEMPOTENCY_KEY_HEADER = "Idempotency-Key";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Aplica apenas para POST
        if (requestContext.getMethod().equals("POST")) {
            String idempotencyKey = requestContext.getHeaderString(IDEMPOTENCY_KEY_HEADER);
            if (idempotencyKey != null) {
                Response cachedResponse = responseCache.getIfPresent(idempotencyKey);
                if (cachedResponse != null) {
                    // Chave já processada, retorna a resposta salva em cache
                    requestContext.abortWith(Response.fromResponse(cachedResponse).build());
                }
            }
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // Aplica apenas para POST bem-sucedidos (201 Created)
        if (requestContext.getMethod().equals("POST") && responseContext.getStatus() == Response.Status.CREATED.getStatusCode()) {
            String idempotencyKey = requestContext.getHeaderString(IDEMPOTENCY_KEY_HEADER);
            if (idempotencyKey != null) {
                // *** A LINHA CORRIGIDA ESTÁ AQUI ***
                // Recriamos a resposta a partir do contexto para poder salvá-la em cache.
                Response responseToCache = Response.status(responseContext.getStatus())
                        .entity(responseContext.getEntity())
                        .location(responseContext.getLocation())
                        .build();
                responseCache.put(idempotencyKey, responseToCache);
            }
        }
    }
}