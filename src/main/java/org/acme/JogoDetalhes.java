package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class JogoDetalhes extends PanacheEntity {

    @Column(length = 1000)
    public String descricao;

    public Double avaliacaoCritica;
}