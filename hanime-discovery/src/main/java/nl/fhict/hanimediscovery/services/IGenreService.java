package nl.fhict.hanimediscovery.services;

import nl.fhict.hanimediscovery.domain.entities.Genre;

import java.util.List;

public interface IGenreService {
    Genre createGenre(Genre genre);

    Genre getGenreById(Long id);

    List<Genre> getAllGenres();

    Genre updateGenre(Genre genre);

    void deleteGenre(Long id);
}
