package be.vdab.muziekadvanced.services;

import be.vdab.muziekadvanced.domain.Album;

import java.util.Optional;

public interface AlbumService {
    Optional<Album> findById(long id);
}
