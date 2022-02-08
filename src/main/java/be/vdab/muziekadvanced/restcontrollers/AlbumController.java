package be.vdab.muziekadvanced.restcontrollers;

import be.vdab.muziekadvanced.domain.Album;
import be.vdab.muziekadvanced.exceptions.AlbumNietGevondenException;
import be.vdab.muziekadvanced.services.AlbumService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.hateoas.server.TypedEntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    EntityModel<Album> get(@PathVariable long id) {
        return service.findById(id)
                .map(album -> EntityModel.of(album,
                        links.linkToItemResource(album),
                        links.linkForItemResource(album)
                                .slash("tracks").withRel("tracks")))
                .orElseThrow(AlbumNietGevondenException::new);
    }

    @ExceptionHandler(AlbumNietGevondenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void albumNietGevonden() {}


}
