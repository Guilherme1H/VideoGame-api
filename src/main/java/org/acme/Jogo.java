package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Jogo extends PanacheEntity {

    @Column(nullable = false)
    public String titulo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TipoGenero genero;

    @Column(nullable = false)
    public LocalDate dataLancamento;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public JogoDetalhes detalhes;

    @ManyToMany(fetch = FetchType.EAGER)
    public List<Desenvolvedora> desenvolvedoras = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    public List<Plataforma> plataformas = new ArrayList<>();

    public static List<Jogo> findByDesenvolvedora(Desenvolvedora desenvolvedora) {
        return find("select j from Jogo j join j.desenvolvedoras d where d = ?1", desenvolvedora).list();
    }
}