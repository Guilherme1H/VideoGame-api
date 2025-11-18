package org.acme;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "DTO para criar ou atualizar um jogo")
public class JogoRequestDTO {

    @NotBlank(message = "O título é obrigatório")
    @Size(min = 2, max = 100)
    @Schema(example = "Cyberpunk 2077")
    public String titulo;

    @NotNull(message = "O gênero é obrigatório")
    @Schema(example = "RPG")
    public TipoGenero genero;

    @NotNull(message = "A data de lançamento é obrigatória")
    @PastOrPresent
    @Schema(example = "2020-12-10")
    public LocalDate dataLancamento;

    @NotEmpty(message = "O jogo deve ter pelo menos uma desenvolvedora")
    @Schema(description = "Lista de IDs das desenvolvedoras existentes")
    public List<Long> desenvolvedoraIds;

    @NotEmpty(message = "O jogo deve estar disponível em pelo menos uma plataforma")
    @Schema(description = "Lista de IDs das plataformas existentes")
    public List<Long> plataformaIds;

    @NotNull(message = "Os detalhes do jogo são obrigatórios")
    @Valid
    public DetalhesRequestDTO detalhes;

    public static class DetalhesRequestDTO {
        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 1000)
        @Schema(example = "Um RPG de ação e aventura em um mundo aberto ambientado na megalópole Night City.")
        public String descricao;

        @NotNull
        @DecimalMin("0.0")
        @DecimalMax("100.0")
        @Schema(example = "76.0")
        public Double avaliacaoCritica;
    }
}