package org.acme;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.GlobalExceptionHandler.ErrorResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/v1/jogos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Jogos", description = "Endpoints para gerenciar jogos.")
public class JogoResource {

    @Inject
    private UriInfoService uriInfoService;

    @GET
    @Operation(summary = "Listar todos os jogos",
            description = "Permite filtrar por gênero ou ordenar por um critério específico.")
    @APIResponse(responseCode = "200", description = "Lista de jogos.")
    public List<JogoRepresentation> listAll(
            @Parameter(name = "genero", description = "Filtra jogos por um gênero específico.", schema = @Schema(implementation = TipoGenero.class))
            @QueryParam("genero") TipoGenero genero,

            @Parameter(name = "ordenarPor", description = "Define o critério de ordenação dos resultados.", schema = @Schema(implementation = TipoOrdenacao.class))
            @QueryParam("ordenarPor") TipoOrdenacao ordenarPor) {

        List<Jogo> jogos;

        if (genero != null) {
            jogos = Jogo.list("genero", genero);
        } else {
            jogos = Jogo.listAll();
        }

        if (ordenarPor != null) {
            if (ordenarPor == TipoOrdenacao.AVALIACAO_DESC) {
                jogos.sort((j1, j2) -> {
                    Double av1 = (j1.detalhes != null) ? j1.detalhes.avaliacaoCritica : 0.0;
                    Double av2 = (j2.detalhes != null) ? j2.detalhes.avaliacaoCritica : 0.0;
                    return Double.compare(av2, av1);
                });
            } else if (ordenarPor == TipoOrdenacao.TITULO_ASC) {
                jogos.sort((j1, j2) -> j1.titulo.compareToIgnoreCase(j2.titulo));
            }
        }

        return jogos.stream()
                .map(j -> JogoRepresentation.fromEntity(j, uriInfoService.getUriInfo()))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar jogo por ID")
    @APIResponse(responseCode = "200", description = "Jogo encontrado.")
    @APIResponse(responseCode = "404", description = "Jogo não encontrado.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public JogoRepresentation findById(@PathParam("id") Long id) {
        Jogo jogo = Jogo.findById(id);
        if (jogo == null) {
            throw new NotFoundException("Jogo com ID " + id + " não encontrado.");
        }
        return JogoRepresentation.fromEntity(jogo, uriInfoService.getUriInfo());
    }

    @POST
    @Transactional
    @Operation(summary = "Cadastrar novo jogo")
    @SecurityRequirement(name = "apiKeyAuth")
    @APIResponse(responseCode = "201", description = "Jogo criado com sucesso", content = @Content(schema = @Schema(implementation = JogoRepresentation.class)))
    @APIResponse(responseCode = "400", description = "Dados de entrada inválidos.")
    @APIResponse(responseCode = "404", description = "Desenvolvedora ou Plataforma não encontrada.")
    public Response create(@Valid JogoRequestDTO dto) {
        Jogo jogo = new Jogo();

        jogo.titulo = dto.titulo;
        jogo.genero = dto.genero;
        jogo.dataLancamento = dto.dataLancamento;
        JogoDetalhes detalhes = new JogoDetalhes();
        detalhes.descricao = dto.detalhes.descricao;
        detalhes.avaliacaoCritica = dto.detalhes.avaliacaoCritica;
        jogo.detalhes = detalhes;
        jogo.desenvolvedoras = Desenvolvedora.list("id in ?1", dto.desenvolvedoraIds);
        jogo.plataformas = Plataforma.list("id in ?1", dto.plataformaIds);

        jogo.persist();
        JogoRepresentation rep = JogoRepresentation.fromEntity(jogo, uriInfoService.getUriInfo());
        return Response.created(uriInfoService.getUriInfo().getRequestUriBuilder().path(jogo.id.toString()).build()).entity(rep).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Atualizar um jogo existente")
    @SecurityRequirement(name = "apiKeyAuth")
    @APIResponse(responseCode = "200", description = "Jogo atualizado com sucesso.")
    public JogoRepresentation update(@PathParam("id") Long id, @Valid JogoRequestDTO dto) {
        Jogo jogo = Jogo.findById(id);
        if (jogo == null) {
            throw new NotFoundException("Jogo com ID " + id + " não encontrado para atualização.");
        }

        jogo.titulo = dto.titulo;
        jogo.genero = dto.genero;
        jogo.dataLancamento = dto.dataLancamento;

        if (jogo.detalhes == null) {
            jogo.detalhes = new JogoDetalhes();
        }

        jogo.detalhes.descricao = dto.detalhes.descricao;
        jogo.detalhes.avaliacaoCritica = dto.detalhes.avaliacaoCritica;

        jogo.desenvolvedoras = Desenvolvedora.list("id in ?1", dto.desenvolvedoraIds);
        jogo.plataformas = Plataforma.list("id in ?1", dto.plataformaIds);

        return JogoRepresentation.fromEntity(jogo, uriInfoService.getUriInfo());
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Deletar um jogo")
    @SecurityRequirement(name = "apiKeyAuth")
    @APIResponse(responseCode = "204", description = "Jogo deletado com sucesso.")
    public Response delete(@PathParam("id") Long id) {
        Jogo jogo = Jogo.findById(id);
        if (jogo == null) {
            throw new NotFoundException("Jogo com ID " + id + " não encontrado para deleção.");
        }
        jogo.delete();
        return Response.noContent().build();
    }
}