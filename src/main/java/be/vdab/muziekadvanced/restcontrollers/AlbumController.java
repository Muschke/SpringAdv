package be.vdab.muziekadvanced.restcontrollers;

import be.vdab.muziekadvanced.domain.Album;
import be.vdab.muziekadvanced.domain.Track;
import be.vdab.muziekadvanced.exceptions.AlbumNietGevondenException;
import be.vdab.muziekadvanced.services.AlbumService;
import org.springframework.hateoas.CollectionModel;
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
    /*het verwarrende aan deze oefening is dat je meermaals hetzelfde weergeeft op andere manier je moet:
    * - albumnaam & artiestnaam teruggeven als string als je zoekt op het id van albumnummer
    * - daarnaast moet je een link weergeven om naar effectieve albumgegevens te gaan, die gaan krak hetzelfde
    * zijn als de eerste gegevens.*/
    EntityModel<AlbumNaamArtiestNaam> get(@PathVariable long id) {
        /*ik wil dat mijn model het model van <ANAN> volgt, (zie private class), gebaseerd op id via url ingegeven*/
        return service.findById(id).map(album ->
                /*zoek id op albumservice, je krijgt album van dat id terug en gaat dat voorts mappen*/
                        EntityModel.of(new AlbumNaamArtiestNaam(album))
                                /*Ik wil dat je ons opgezocht album in deeltjes snijd(mappen) in de juiste delen in ons
                                * ANAN class steekt, die mag je ook terugbrengen*/
                                .add(links.linkToItemResource(album))
                                /*Dan wil ik dat je daar links bijsteekt, deze staat letterlijk in cursus p60
                                * onderaan pagina uitgelegd, dit bevat exact dezelfde data alsdat al wordt weergeven*/
                                .add(links.linkForItemResource(album).slash( "tracks").withRel("tracks")))
                /*Dan wil ik dat je daar tweede link bijsteekt.  Nu de link is voorgedaan, dus we nemen hetzelfde
                * als de vorige links en voegen daar /tracks aan toe BAM link is klaar zegt de cursus.
                * Dat is niet waar!! Die withRel is fout uitgelegd in de cursus. uw withRel moet die link
                * nog specifiek benoemen anders dan 'self' of het werkt niet, hoe dat je die noemt is niet relevant
                * nuttig is hetzelfde (bijvoorbeeld tracks) maar blabla werkt evengoed*/
                .orElseThrow(AlbumNietGevondenException::new);
    }

    @GetMapping("{id}/tracks")
    CollectionModel<Track> getTracks(@PathVariable long id) {
        /*voor embedd dingen weer te geven moet je CollectionModel gebruiken, de rest werkt exact hetzelfde
        * let vooral op hoe met de links en de getmapping wordt gespeeld, dat moet je wel beethebben, dat moet juist zitten
        * helaas zet de cursus weinig in op die controllers*/
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
