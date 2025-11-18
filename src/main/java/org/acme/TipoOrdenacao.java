package org.acme;

public enum TipoOrdenacao {
    TITULO_ASC("Título (A-Z)"),
    AVALIACAO_DESC("Melhor Avaliação");

    private final String descricao;

    TipoOrdenacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}