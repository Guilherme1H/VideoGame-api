package org.acme;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Provider
public class IdempotencyFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Cache<String, Response> responseCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!requestContext.getMethod().equalsIgnoreCase("POST")) {
            return;
        }

        String idempotencyKey = requestContext.getHeaderString("Idempotency-Key");
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            return;
        }

        Response cachedResponse = responseCache.getIfPresent(idempotencyKey);
        if (cachedResponse != null) {
            requestContext.abortWith(Response.fromResponse(cachedResponse).build());
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (!requestContext.getMethod().equalsIgnoreCase("POST") || responseContext.getStatus() / 100 != 2) {
            return;
        }

        String idempotencyKey = requestContext.getHeaderString("Idempotency-Key");
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            if (responseCache.getIfPresent(idempotencyKey) == null) {
                Response responseToCache = Response.status(responseContext.getStatus())
                        .entity(responseContext.getEntity())
                        .replaceAll(responseContext.getHeaders())
                        .build();
                responseCache.put(idempotencyKey, responseToCache);
            }
        }
    }
}