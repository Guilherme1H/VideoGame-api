package org.acme;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.Idempotent;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/v1/plataformas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Plataformas", description = "Endpoints para gerenciar plataformas de jogos.")
public class PlataformaResource {

    @Inject
    private UriInfoService uriInfoService;

    @GET
    @Operation(summary = "Listar todas as plataformas")
    public List<PlataformaRepresentation> listAll() {
        return Plataforma.listAll().stream()
                .map(p -> PlataformaRepresentation.fromEntity((Plataforma) p, uriInfoService.getUriInfo()))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar plataforma por ID")
    public PlataformaRepresentation findById(@PathParam("id") Long id) {
        Plataforma plataforma = Plataforma.findById(id);
        if (plataforma == null) {
            throw new NotFoundException("Plataforma não encontrada.");
        }
        return PlataformaRepresentation.fromEntity(plataforma, uriInfoService.getUriInfo());
    }

    @POST
    @Transactional
    @Idempotent
    @Operation(summary = "Cadastrar nova plataforma")
    @SecurityRequirement(name = "apiKeyAuth")
    @APIResponse(responseCode = "201", description = "Plataforma criada com sucesso", content = @Content(schema = @Schema(implementation = PlataformaRepresentation.class)))
    public Response create(Plataforma plataforma) {
        plataforma.persist();
        PlataformaRepresentation rep = PlataformaRepresentation.fromEntity(plataforma, uriInfoService.getUriInfo());
        return Response.created(uriInfoService.getUriInfo().getRequestUriBuilder().path(plataforma.id.toString()).build()).entity(rep).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Atualizar uma plataforma existente")
    @SecurityRequirement(name = "apiKeyAuth")
    public PlataformaRepresentation update(@PathParam("id") Long id, Plataforma plataformaData) {
        Plataforma plataforma = Plataforma.findById(id);
        if (plataforma == null) {
            throw new NotFoundException("Plataforma não encontrada para atualização.");
        }
        plataforma.nome = plataformaData.nome;
        plataforma.fabricante = plataformaData.fabricante;
        plataforma.anoLancamento = plataformaData.anoLancamento;
        return PlataformaRepresentation.fromEntity(plataforma, uriInfoService.getUriInfo());
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Deletar uma plataforma")
    @SecurityRequirement(name = "apiKeyAuth")
    public Response delete(@PathParam("id") Long id) {
        Plataforma plataforma = Plataforma.findById(id);
        if (plataforma == null) {
            throw new NotFoundException("Plataforma não encontrada para deleção.");
        }
        plataforma.delete();
        return Response.noContent().build();
    }
}