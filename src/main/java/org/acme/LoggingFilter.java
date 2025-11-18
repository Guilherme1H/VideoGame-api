package org.acme;

import jakarta.annotation.Priority;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
@Priority(0)
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOG = Logger.getLogger(LoggingFilter.class);
    private static final String START_TIME_PROPERTY = "start-time";

    @Context
    UriInfo uriInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        requestContext.setProperty(START_TIME_PROPERTY, System.currentTimeMillis());
        LOG.infof("Requisição recebida: %s %s", requestContext.getMethod(), uriInfo.getPath());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        long startTime = (long) requestContext.getProperty(START_TIME_PROPERTY);
        long duration = System.currentTimeMillis() - startTime;
        LOG.infof("Requisição finalizada: %s %s com status %d em %d ms",
                requestContext.getMethod(), uriInfo.getPath(), responseContext.getStatus(), duration);
    }
}