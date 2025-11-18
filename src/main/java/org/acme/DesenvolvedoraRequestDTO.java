package org.acme;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "DesenvolvedoraRequest", description = "DTO para criar ou atualizar uma desenvolvedora.")
public class DesenvolvedoraRequestDTO {

    @NotBlank(message = "O nome da desenvolvedora não pode ser vazio.")
    @Schema(example = "Naughty Dog", required = true)
    public String nome;

    @NotBlank(message = "O país de origem não pode ser vazio.")
    @Schema(example = "EUA", required = true)
    public String paisOrigem;

    @NotNull(message = "O ano de fundação é obrigatório.")
    @Min(value = 1950, message = "O ano de fundação deve ser após 1950.")
    @Max(value = 2025, message = "O ano de fundação não pode ser no futuro.")
    @Schema(example = "1984", required = true)
    public Integer anoFundacao;

    @NotNull(message = "O ID da publisher é obrigatório.")
    @Schema(description = "ID da Publisher existente à qual esta desenvolvedora pertence.", example = "1", required = true)
    public Long publisherId;
}