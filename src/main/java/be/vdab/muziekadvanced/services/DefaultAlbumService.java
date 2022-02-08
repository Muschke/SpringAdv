package be.vdab.muziekadvanced.services;

import be.vdab.muziekadvanced.domain.Album;
import be.vdab.muziekadvanced.repositories.AlbumRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DefaultAlbumService implements AlbumService{
    private final AlbumRepository repository;

    public DefaultAlbumService(AlbumRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Album> findById(long id){
        return repository.findById(id);
    }
}
