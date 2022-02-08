package be.vdab.muziekadvanced.domain;

import be.vdab.muziekadvanced.exceptions.TrackAlInAlbumException;
import be.vdab.muziekadvanced.exceptions.TrackNietInAlbumException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.sql.Time;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {
    private Artiest artiest;
    private Album album;
    private Album album2;
    private Track track;
    private Track track2;

    @BeforeEach
    void beforeEach() {
        artiest = new Artiest("testArtiestTwee");
        album = new Album(artiest, "testAlbumTwee" , 7);
        album2 = new Album(artiest, "testAlbumDrie", 8);
        track = new Track("testTrack", LocalTime.of(0, 3, 30));
        track2 = new Track("testTrackTwee", LocalTime.of(0, 3, 30));
    }

    @Test
    void artiestZitInAlbum() {
        assertThat(album.getArtiest()).isEqualTo(artiest);
    }

    @Test
    void eenArtiestKanMeerdereAlbumsHebben() {
        assertThat(album.getArtiest()).isEqualTo(artiest);
        assertThat(album2.getArtiest()).isEqualTo(artiest);
    }

    @Test
    void eenNieuwAlbumHeeftGeenTracks() {
        assertThat(album.getTracks()).isEmpty();
        assertThat(album2.getTracks()).isEmpty();
    }

    @Test
    void eenNullTrackToevoegenKanNiet() {
        assertThatNullPointerException().isThrownBy(
                () -> album.addTrack(null));
    };
    @Test
    void eenBestaandeTrackToevoegenKanNiet() {
        album.addTrack(track);
        assertThatExceptionOfType(TrackAlInAlbumException.class).isThrownBy(
                ()-> album.addTrack(track));
    };

    @Test
    void eenNietBestaandeTrackVerwijderenKanNiet() {
        album.addTrack(track);
        assertThatExceptionOfType(TrackNietInAlbumException.class).isThrownBy(
                () -> album.removeTrack(track2));
    };
    @Test
    void eenTrackToevoegenWerkt() {
        assertThat(album.addTrack(track));
        assertThat(album.getTracks()).containsOnly(track);

    };
    @Test
    void eenTrackVerwijderenWerkt() {
        album.addTrack(track);
        album.addTrack(track2);
        assertThat(album.getTracks()).contains(track, track2);
        album.removeTrack(track);
        album.removeTrack(track2);
        assertThat(album.getTracks()).isEmpty();
    };
}