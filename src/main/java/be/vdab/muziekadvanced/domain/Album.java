package be.vdab.muziekadvanced.domain;

import be.vdab.muziekadvanced.exceptions.TrackAlInAlbumException;
import be.vdab.muziekadvanced.exceptions.TrackNietInAlbumException;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "albums")
@NamedEntityGraph(name = "Album.metArtiest",
        attributeNodes = @NamedAttributeNode("artiest"))
public class Album {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "artiestId")
    private Artiest artiest;
    private String naam;
    @ElementCollection
    @CollectionTable(name = "tracks",
    joinColumns = @JoinColumn(name = "albumId"))
    private Set<Track> tracks;



    public Album(Artiest artiest, String naam) {
        this.artiest = artiest;
        this.naam = naam;
        this.tracks = new LinkedHashSet<>();
    }

    protected Album() {};

    public long getId() {
        return id;
    }

    public Artiest getArtiest() {
        return artiest;
    }

    public String getNaam() {
        return naam;
    }


    public boolean addTrack(Track track) {
        if(track.equals(null)) {
            throw new NullPointerException();
        }
        if(tracks.contains(track)) {
            throw new TrackAlInAlbumException();
        }
        return tracks.add(track);
    }

    public boolean removeTrack(Track track) {
        if(track.equals(null)) {
            throw new NullPointerException();
        }
        if(!tracks.contains(track)) {
            throw new TrackNietInAlbumException();
        }
        return tracks.remove(track);
    }

    public Set<Track> getTracks() {
        return Collections.unmodifiableSet(tracks);
    }
}
