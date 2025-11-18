package org.acme;

import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.HashMap;
import java.util.Map;

@Schema(description = "Representação de uma desenvolvedora com links HATEOAS")
public class DesenvolvedoraRepresentation {

    @Schema(readOnly = true)
    public Long id;

    @Schema(example = "CD Projekt Red")
    public String nome;

    @Schema(example = "Polônia")
    public String paisOrigem;

    @Schema(example = "2002")
    public Integer anoFundacao;

    @Schema(readOnly = true)
    public Map<String, String> _links = new HashMap<>();

    public void addLink(String rel, String href) {
        this._links.put(rel, href);
    }

    public static DesenvolvedoraRepresentation fromEntity(Desenvolvedora desenvolvedora, UriInfo uriInfo) {
        if (desenvolvedora == null) {
            return null;
        }
        DesenvolvedoraRepresentation rep = new DesenvolvedoraRepresentation();
        rep.id = desenvolvedora.id;
        rep.nome = desenvolvedora.nome;
        rep.paisOrigem = desenvolvedora.paisOrigem;
        rep.anoFundacao = desenvolvedora.anoFundacao;

        UriBuilder baseUri = uriInfo.getBaseUriBuilder().path(DesenvolvedoraResource.class);
        rep.addLink("self", baseUri.path(desenvolvedora.id.toString()).build().toString());
        rep.addLink("all", baseUri.build().toString());
        rep.addLink("update", baseUri.path(desenvolvedora.id.toString()).build().toString());
        rep.addLink("delete", baseUri.path(desenvolvedora.id.toString()).build().toString());

        rep.addLink("jogos_desta_desenvolvedora", uriInfo.getBaseUriBuilder().path(JogoResource.class).path("search").queryParam("desenvolvedoraId", desenvolvedora.id).build().toString());

        return rep;
    }
}