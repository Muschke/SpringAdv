package be.vdab.muziekadvanced.domain;

import javax.persistence.*;

@Entity
@Table(name = "artiesten")
public class Artiest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;

    public Artiest(String naam) {
        this.naam = naam;
    }

    protected Artiest() {};

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }
}
