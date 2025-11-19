package org.acme;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.Idempotent;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.GlobalExceptionHandler.ErrorResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/v1/desenvolvedoras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Desenvolvedoras", description = "Endpoints para gerenciar desenvolvedoras de jogos.")
public class DesenvolvedoraResource {

    @Inject
    private UriInfoService uriInfoService;

    @GET
    @Operation(summary = "Listar todas as desenvolvedoras", description = "Retorna uma lista de todas as desenvolvedoras cadastradas.")
    @APIResponse(responseCode = "200", description = "Lista de desenvolvedoras.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DesenvolvedoraRepresentation[].class)))
    public List<DesenvolvedoraRepresentation> listAll() {
        return Desenvolvedora.listAll().stream()
                .map(d -> DesenvolvedoraRepresentation.fromEntity((Desenvolvedora) d, uriInfoService.getUriInfo()))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar desenvolvedora por ID")
    @APIResponse(responseCode = "200", description = "Desenvolvedora encontrada.", content = @Content(schema = @Schema(implementation = DesenvolvedoraRepresentation.class)))
    @APIResponse(responseCode = "404", description = "Desenvolvedora não encontrada.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public DesenvolvedoraRepresentation findById(@PathParam("id") Long id) {
        Desenvolvedora desenvolvedora = Desenvolvedora.findById(id);
        if (desenvolvedora == null) {
            throw new NotFoundException("Desenvolvedora com ID " + id + " não encontrada.");
        }
        return DesenvolvedoraRepresentation.fromEntity(desenvolvedora, uriInfoService.getUriInfo());
    }

    @POST
    @Transactional
    @Idempotent
    @Operation(summary = "Cadastrar nova desenvolvedora")
    @SecurityRequirement(name = "apiKeyAuth")
    @APIResponse(responseCode = "201", description = "Desenvolvedora criada com sucesso.", content = @Content(schema = @Schema(implementation = DesenvolvedoraRepresentation.class)))
    @APIResponse(responseCode = "400", description = "Dados de entrada inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @APIResponse(responseCode = "401", description = "Chave de API inválida.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @APIResponse(responseCode = "404", description = "Publisher não encontrada.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public Response create(@Valid DesenvolvedoraRequestDTO dto) {
        Publisher publisher = Publisher.findById(dto.publisherId);
        if (publisher == null) {
            throw new NotFoundException("Publisher com ID " + dto.publisherId + " não encontrada.");
        }

        Desenvolvedora desenvolvedora = new Desenvolvedora();
        desenvolvedora.nome = dto.nome;
        desenvolvedora.paisOrigem = dto.paisOrigem;
        desenvolvedora.anoFundacao = dto.anoFundacao;
        desenvolvedora.publisher = publisher; // Associando
        desenvolvedora.persist();

        DesenvolvedoraRepresentation rep = DesenvolvedoraRepresentation.fromEntity(desenvolvedora, uriInfoService.getUriInfo());
        return Response.created(uriInfoService.getUriInfo().getRequestUriBuilder().path(desenvolvedora.id.toString()).build()).entity(rep).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Atualizar uma desenvolvedora existente")
    @SecurityRequirement(name = "apiKeyAuth")
    @APIResponse(responseCode = "200", description = "Desenvolvedora atualizada com sucesso.", content = @Content(schema = @Schema(implementation = DesenvolvedoraRepresentation.class)))
    @APIResponse(responseCode = "400", description = "Dados de entrada inválidos.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @APIResponse(responseCode = "404", description = "Desenvolvedora ou Publisher não encontrada.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @APIResponse(responseCode = "401", description = "Chave de API inválida.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public DesenvolvedoraRepresentation update(@PathParam("id") Long id, @Valid DesenvolvedoraRequestDTO dto) {
        Desenvolvedora desenvolvedora = Desenvolvedora.findById(id);
        if (desenvolvedora == null) {
            throw new NotFoundException("Desenvolvedora com ID " + id + " não encontrada para atualização.");
        }

        Publisher publisher = Publisher.findById(dto.publisherId);
        if (publisher == null) {
            throw new NotFoundException("Publisher com ID " + dto.publisherId + " não encontrada.");
        }

        desenvolvedora.nome = dto.nome;
        desenvolvedora.paisOrigem = dto.paisOrigem;
        desenvolvedora.anoFundacao = dto.anoFundacao;
        desenvolvedora.publisher = publisher;

        return DesenvolvedoraRepresentation.fromEntity(desenvolvedora, uriInfoService.getUriInfo());
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Deletar uma desenvolvedora")
    @SecurityRequirement(name = "apiKeyAuth")
    @APIResponse(responseCode = "204", description = "Desenvolvedora deletada com sucesso.")
    @APIResponse(responseCode = "404", description = "Desenvolvedora não encontrada.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @APIResponse(responseCode = "401", description = "Chave de API inválida.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public Response delete(@PathParam("id") Long id) {
        Desenvolvedora desenvolvedora = Desenvolvedora.findById(id);
        if (desenvolvedora == null) {
            throw new NotFoundException("Desenvolvedora com ID " + id + " não encontrada para deleção.");
        }
        desenvolvedora.delete();
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/jogos")
    @Operation(summary = "Listar todos os jogos de uma desenvolvedora específica")
    @APIResponse(responseCode = "200", description = "Lista de jogos da desenvolvedora.", content = @Content(schema = @Schema(implementation = JogoRepresentation[].class)))
    @APIResponse(responseCode = "404", description = "Desenvolvedora não encontrada.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public List<JogoRepresentation> getJogosByDesenvolvedora(@PathParam("id") Long id) {
        Desenvolvedora desenvolvedora = Desenvolvedora.findById(id);
        if (desenvolvedora == null) {
            throw new NotFoundException("Desenvolvedora com ID " + id + " não encontrada.");
        }
        return Jogo.findByDesenvolvedora(desenvolvedora).stream()
                .map(j -> JogoRepresentation.fromEntity(j, uriInfoService.getUriInfo()))
                .collect(Collectors.toList());
    }
}