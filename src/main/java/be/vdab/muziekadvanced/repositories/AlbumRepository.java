package be.vdab.muziekadvanced.repositories;

import be.vdab.muziekadvanced.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
