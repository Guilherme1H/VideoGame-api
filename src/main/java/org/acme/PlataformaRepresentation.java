package org.acme;

import jakarta.ws.rs.core.UriInfo;

public class PlataformaRepresentation {
    public Long id;
    public String nome;
    public String fabricante;
    public int anoLancamento;
    public Links _links;

    public static PlataformaRepresentation fromEntity(Plataforma plat, UriInfo uriInfo) {
        PlataformaRepresentation rep = new PlataformaRepresentation();
        rep.id = plat.id;
        rep.nome = plat.nome;
        rep.fabricante = plat.fabricante;
        rep.anoLancamento = plat.anoLancamento;

        String selfUri = uriInfo.getBaseUriBuilder().path("api/v1/plataformas/{id}").build(plat.id).toString();
        String allUri = uriInfo.getBaseUriBuilder().path("api/v1/plataformas").build().toString();
        rep._links = new Links(selfUri, allUri);

        return rep;
    }
}