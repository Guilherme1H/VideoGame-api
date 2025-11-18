package org.acme;

import jakarta.persistence.Embeddable;

@Embeddable
public class DetalhesJogo {

    private String descricao;
    private Double avaliacaoCritica;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getAvaliacaoCritica() {
        return avaliacaoCritica == null ? 0.0 : avaliacaoCritica;
    }

    public void setAvaliacaoCritica(Double avaliacaoCritica) {
        this.avaliacaoCritica = avaliacaoCritica;
    }
}