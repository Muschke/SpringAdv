package be.vdab.muziekadvanced.restcontrollers;

import be.vdab.muziekadvanced.domain.Album;
import be.vdab.muziekadvanced.services.AlbumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/albums")
class AlbumController {
    private final AlbumService service;

    public AlbumController(AlbumService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    Album get(@PathVariable long id) {
        return service.findById(id).get();
    }

}
