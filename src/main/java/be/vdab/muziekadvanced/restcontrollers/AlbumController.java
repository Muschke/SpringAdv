package be.vdab.muziekadvanced.restcontrollers;

import be.vdab.muziekadvanced.domain.Album;
import be.vdab.muziekadvanced.exceptions.AlbumNietGevondenException;
import be.vdab.muziekadvanced.services.AlbumService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.hateoas.server.TypedEntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Track;
import java.util.Collections;

@RestController
@RequestMapping("/albums")
@ExposesResourceFor(Album.class)
class AlbumController {
    private final AlbumService service;
    private final TypedEntityLinks.ExtendedTypedEntityLinks<Album> links;

    public AlbumController(AlbumService service, EntityLinks links) {
        this.service = service;
        this.links = links.forType(Album.class, Album::getId);
    }

    @GetMapping("{id}")
    EntityModel<AlbumNaamArtiestNaam> get(@PathVariable long id) {
        return service.findById(id).map(album ->
                        EntityModel.of(new AlbumNaamArtiestNaam(album))
                                .add(links.linkToItemResource(album))
                                .add(links.linkForItemResource(album).slash( "tracks").withRel("tracks")))
                .orElseThrow(AlbumNietGevondenException::new);
    }
    @GetMapping("{id}/tracks")
    CollectionModel<Track> getTracks(@PathVariable long id) {
        return service.findById(id).map(album ->
                        CollectionModel.of(album.getTracks())
                                .add(links.linkForItemResource(album).slash("tracks").withRel("self"))
                                .add(links.linkToItemResource(album).withRel("album")))
                .orElseThrow(AlbumNietGevondenException::new);
    }

    @ExceptionHandler(AlbumNietGevondenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void albumNietGevonden() {}

/*private class maken om dto weer te geven*/
    private static class AlbumNaamArtiestNaam {
    private final String albumNaam;
    private final String artiestNaam;

    AlbumNaamArtiestNaam(Album album) {
        this.albumNaam = album.getNaam();
        this.artiestNaam = album.getArtiest().getNaam();
    }

    public String getAlbumNaam() {
        return albumNaam;
    }
    public String getArtiestNaam() {
        return artiestNaam;
    }

    }
}
