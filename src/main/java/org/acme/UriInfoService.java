package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

@ApplicationScoped
public class UriInfoService {

    @Context
    private UriInfo uriInfo;

    public UriInfo getUriInfo() {
        return uriInfo;
    }
}