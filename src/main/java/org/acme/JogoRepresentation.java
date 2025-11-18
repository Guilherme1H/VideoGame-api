package org.acme;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class JogoRepresentation {

    @Schema(description = "ID único do jogo", readOnly = true)
    private Long id;

    @Schema(description = "Título do jogo")
    private String titulo;

    @Schema(description = "Gênero do jogo")
    private String genero;

    @Schema(description = "Data de lançamento do jogo")
    private LocalDate dataLancamento;

    @Schema(description = "Breve descrição do jogo")
    private String descricao;

    @Schema(description = "Nota da crítica especializada (0 a 100)")
    private Double avaliacaoCritica;

    @Schema(description = "Lista de IDs das desenvolvedoras")
    private List<Long> desenvolvedoraIds;

    @Schema(description = "Lista de IDs das plataformas")
    private List<Long> plataformaIds;

    @Schema(description = "Links HATEOAS para navegação", readOnly = true)
    private Links _links;


    public static JogoRepresentation fromEntity(Jogo jogo, UriInfo uriInfo) {
        JogoRepresentation rep = new JogoRepresentation();
        rep.id = jogo.id;
        rep.titulo = jogo.titulo;
        rep.genero = jogo.genero.name();
        rep.dataLancamento = jogo.dataLancamento;
        if (jogo.detalhes != null) {
            rep.descricao = jogo.detalhes.descricao;
            rep.avaliacaoCritica = jogo.detalhes.avaliacaoCritica;
        }

        rep.desenvolvedoraIds = jogo.desenvolvedoras.stream()
                .map(d -> d.id)
                .collect(Collectors.toList());

        rep.plataformaIds = jogo.plataformas.stream()
                .map(p -> p.id)
                .collect(Collectors.toList());

        URI selfUri = uriInfo.getBaseUriBuilder()
                .path("api/v1/jogos")
                .path(Long.toString(jogo.id))
                .build();
        URI allUri = uriInfo.getBaseUriBuilder()
                .path("api/v1/jogos")
                .build();

        rep._links = new Links();
        rep._links.setSelf(selfUri.toString());
        rep._links.setAll(allUri.toString());

        return rep;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public LocalDate getDataLancamento() { return dataLancamento; }
    public String getDescricao() { return descricao; }
    public Double getAvaliacaoCritica() { return avaliacaoCritica; }
    public List<Long> getDesenvolvedoraIds() { return desenvolvedoraIds; }
    public List<Long> getPlataformaIds() { return plataformaIds; }
    public Links get_links() { return _links; }

    @Schema(hidden = true)
    static class Links {
        private String self;
        private String all;

        public String getSelf() { return self; }
        public void setSelf(String self) { this.self = self; }
        public String getAll() { return all; }
        public void setAll(String all) { this.all = all; }
    }
}