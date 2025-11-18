package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Desenvolvedora extends PanacheEntity {

    public String nome;
    public String paisOrigem;
    public Integer anoFundacao;

    @ManyToOne
    public Publisher publisher;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Desenvolvedora that = (Desenvolvedora) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}