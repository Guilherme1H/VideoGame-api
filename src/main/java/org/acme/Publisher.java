package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Publisher extends PanacheEntity {
    public String nome;
    public Integer anoFundacao;
}