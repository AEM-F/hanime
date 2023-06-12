package nl.fhict.hanimediscovery.services;

import lombok.AllArgsConstructor;
import nl.fhict.hanimediscovery.domain.entities.Genre;
import nl.fhict.hanimediscovery.repositories.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class GenreService implements IGenreService {
    private final GenreRepository genreRepository;

    @Override
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre getGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Genre not found"));
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre updateGenre(Genre genre) {
        if (!genreRepository.existsById(genre.getId())) {
            throw new NoSuchElementException("Genre not found");
        }
        return genreRepository.save(genre);
    }

    @Override
    public void deleteGenre(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new NoSuchElementException("Genre not found");
        }
        genreRepository.deleteById(id);
    }
}

