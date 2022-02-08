package be.vdab.muziekadvanced.domain;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
class Track {
    private String naam;
    private LocalTime tijd;

    public Track(String naam, LocalTime tijd) {
        this.naam = naam;
        this.tijd = tijd;
    }

    protected Track() {};


    public String getNaam() {
        return naam;
    }

    public LocalTime getTijd() {
        return tijd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Track)) return false;
        Track track = (Track) o;
        return Objects.equals(naam.toUpperCase(), track.naam.toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(naam.toUpperCase());
    }
}
