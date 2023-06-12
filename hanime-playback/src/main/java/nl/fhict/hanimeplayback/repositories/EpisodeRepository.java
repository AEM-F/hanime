package nl.fhict.hanimeplayback.repositories;

import nl.fhict.hanimeplayback.entities.Episode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    boolean existsByAnimeId(Long animeId);
    void deleteAllByAnimeId(Long animeId);
    List<Episode> findAllByAnimeId(Long animeId);
    Page<Episode> findByReleaseDateAndSeasonAndAnimeId(LocalDateTime releaseDate,
                                                       Integer seasonNumber,
                                                       Long animeId,
                                                       Pageable pageable);

    Page<Episode> findByReleaseDateAndAnimeId(LocalDateTime releaseDate, Long animeId, Pageable pageable);

    Page<Episode> findBySeasonAndAnimeId(Integer seasonNumber, Long animeId, Pageable pageable);
    Page<Episode> findAllByAnimeId(Long animeId, Pageable pageable);
}
