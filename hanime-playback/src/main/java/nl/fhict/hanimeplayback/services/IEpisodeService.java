package nl.fhict.hanimeplayback.services;

import nl.fhict.hanimeplayback.entities.Episode;
import nl.fhict.hanimeplayback.web.dto.RequestEpisode;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface IEpisodeService {
    Episode createEpisode(RequestEpisode episodeR);

    boolean deleteEpisodeByAnimeId(Long animeId);

    Episode getEpisodeById(Long id);

    List<Episode> getAllByAnimeId(Long id);

    List<Episode> getAllEpisodes();

    Episode updateEpisode(Long id, RequestEpisode episodeR);

    void deleteEpisode(Long id);

    Page<Episode> getEpisodesByPagination(int page, int size, LocalDateTime releaseDate, Integer seasonNumber, Long animeId);
}
