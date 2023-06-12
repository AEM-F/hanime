package nl.fhict.hanimediscovery.repositories;

import nl.fhict.hanimediscovery.domain.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}

