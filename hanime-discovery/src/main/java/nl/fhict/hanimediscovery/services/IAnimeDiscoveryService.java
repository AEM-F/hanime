package nl.fhict.hanimediscovery.services;

import nl.fhict.hanimediscovery.domain.entities.Anime;
import nl.fhict.hanimediscovery.domain.entities.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public interface IAnimeDiscoveryService {

    Page<Anime> getAllAnimes(PageRequest pageRequest);

    Anime createAnime(Anime anime);

    Anime getAnimeById(Long id);

    Anime updateAnime(Long id, Anime updatedAnime);

    void deleteAnime(Long id);

    Page<Anime> getAnimesByCriteria(
            List<Genre> genres,
            Boolean isUpcoming,
            String animeYear,
            String animeSeason,
            String animeSynopsis,
            LocalDateTime airedFrom,
            LocalDateTime airedTo,
            String animeStatus,
            String animeType,
            String titleEnglish,
            String titleJapanese,
            Pageable pageable);
}
