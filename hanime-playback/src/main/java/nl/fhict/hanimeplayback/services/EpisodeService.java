package nl.fhict.hanimeplayback.services;

import lombok.AllArgsConstructor;
import nl.fhict.hanimeplayback.entities.Episode;
import nl.fhict.hanimeplayback.repositories.EpisodeRepository;
import nl.fhict.hanimeplayback.web.dto.RequestEpisode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class EpisodeService implements IEpisodeService {
    private final EpisodeRepository episodeRepository;


    @Override
    public Episode createEpisode(RequestEpisode episodeR) {
        Episode episode = new Episode();
        episode.setReleaseDate(episodeR.getReleaseDate());
        episode.setSeason(episodeR.getSeasonNr());
        episode.setEpisodeUrls(episodeR.getEpisodeUrls());
        episode.setAnimeId(episodeR.getAnimeId());
        return episodeRepository.save(episode);
    }

    @Override
    public boolean deleteEpisodeByAnimeId(Long animeId) {
        if(!episodeRepository.existsByAnimeId(animeId)){
            throw new NoSuchElementException("Episodes not found");
        }
        episodeRepository.deleteAllByAnimeId(animeId);
        return true;
    }

    @Override
    public Episode getEpisodeById(Long id) {
        return episodeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Episode not found"));
    }

    @Override
    public List<Episode> getAllByAnimeId(Long id) {
        if(!episodeRepository.existsByAnimeId(id)){
            throw new NoSuchElementException("Episode not found");
        }
        return episodeRepository.findAllByAnimeId(id);
    }

    @Override
    public List<Episode> getAllEpisodes() {
        return episodeRepository.findAll();
    }

    @Override
    public Episode updateEpisode(Long id, RequestEpisode episodeR) {
        if (!episodeRepository.existsById(id)) {
            throw new NoSuchElementException("Episode not found");
        }
        Episode epToSave = new Episode();
        epToSave.setReleaseDate(episodeR.getReleaseDate());
        epToSave.setSeason(episodeR.getSeasonNr());
        epToSave.setEpisodeUrls(episodeR.getEpisodeUrls());
        epToSave.setAnimeId(episodeR.getAnimeId());
        return episodeRepository.save(epToSave);
    }

    @Override
    public void deleteEpisode(Long id) {
        if (!episodeRepository.existsById(id)) {
            throw new NoSuchElementException("Episode not found");
        }
        episodeRepository.deleteById(id);
    }

    @Override
    public Page<Episode> getEpisodesByPagination(int page, int size, LocalDateTime releaseDate, Integer seasonNumber, Long animeId) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (releaseDate != null && seasonNumber != null) {
            return episodeRepository.findByReleaseDateAndSeasonAndAnimeId(releaseDate, seasonNumber, animeId,pageRequest);
        } else if (releaseDate != null) {
            return episodeRepository.findByReleaseDateAndAnimeId(releaseDate, animeId, pageRequest);
        } else if (seasonNumber != null) {
            return episodeRepository.findBySeasonAndAnimeId(seasonNumber, animeId, pageRequest);
        } else {
            return episodeRepository.findAllByAnimeId(animeId, pageRequest);
        }
    }
}
