package nl.fhict.hanimeplayback.web.resource;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.fhict.hanimeplayback.entities.Episode;
import nl.fhict.hanimeplayback.services.IEpisodeService;
import nl.fhict.hanimeplayback.web.dto.RequestEpisode;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/episode")
@AllArgsConstructor
@Slf4j
public class EpisodeController {
    private final IEpisodeService episodeService;

    @PostMapping
    @Timed(value = "createEpisode.time", description = "Time taken to create episode")
    public ResponseEntity<Episode> createEpisode(@RequestHeader("hanime-correlation-id") String correlationid,
                                                 @Valid @RequestBody RequestEpisode episode) {
        log.info("Action: Creating a new episode" + ";Correlation id: " + correlationid + "Episode: "+ episode.toString());
        Episode createdEpisode = episodeService.createEpisode(episode);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEpisode);
    }

    @GetMapping("/{id}")
    @Timed(value = "getEpisodeById.time", description = "Time taken to get episode by id")
    public ResponseEntity<Episode> getEpisodeById(@RequestHeader("hanime-correlation-id") String correlationid,
                                                  @PathVariable("id") Long id) {
        Episode episode = episodeService.getEpisodeById(id);
        return ResponseEntity.ok(episode);
    }

    @GetMapping("/anime/{id}")
    @Timed(value = "getAllByAnimeId.time", description = "Time taken to get episodes by anime id")
    public ResponseEntity<List<Episode>> getAllByAnimeId(@RequestHeader("hanime-correlation-id") String correlationid,
                                                  @PathVariable("id") Long id) {
        log.info("Action: getting all episodes by anime id: "+ id.toString() + ";Correlation id: " + correlationid);
        return ResponseEntity.ok(episodeService.getAllByAnimeId(id));
    }

    @GetMapping
    @Timed(value = "getAllEpisodes.time", description = "Time taken to get all epsiodes")
    public ResponseEntity<List<Episode>> getAllEpisodes(@RequestHeader("hanime-correlation-id") String correlationid) {
        log.info("Action: getting all episodes" + ";Correlation id: " + correlationid);
        List<Episode> episodes = episodeService.getAllEpisodes();
        return ResponseEntity.ok(episodes);
    }

    @PutMapping("/{id}")
    @Timed(value = "updateEpisode.time", description = "Time taken to update episode")
    public ResponseEntity<Episode> updateEpisode(@RequestHeader("hanime-correlation-id") String correlationid,
                                                 @PathVariable("id") Long id,
                                                 @Valid @RequestBody RequestEpisode episode) {
        log.info("Action: Updating all episodes by anime id: "+ id.toString() + ";Correlation id: " + correlationid + "Episode: "+ episode.toString());
        Episode updatedEpisode = episodeService.updateEpisode(id, episode);
        return ResponseEntity.ok(updatedEpisode);
    }

    @DeleteMapping("/{id}")
    @Timed(value = "deleteEpisode.time", description = "Time taken to delete episode")
    public ResponseEntity<Void> deleteEpisode(@RequestHeader("hanime-correlation-id") String correlationid,
                                              @PathVariable("id") Long id) {
        episodeService.deleteEpisode(id);
        return ResponseEntity.noContent().build();
    }

    //@OpenAPI31("Get episodes by pagination, release date, and season number")
    @GetMapping("/{animeId}/search")
    @Timed(value = "getEpisodesByPagination.time", description = "Time taken to get episodes by pagination")
    public ResponseEntity<Page<Episode>> getEpisodesByPagination(
            @RequestHeader("hanime-correlation-id") String correlationid,
            @PathVariable Long animeId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "releaseDate", required = false) LocalDateTime releaseDate,
            @RequestParam(value = "seasonNumber", required = false) Integer seasonNumber) {
        Page<Episode> episodes = episodeService.getEpisodesByPagination(page, size, releaseDate, seasonNumber, animeId);
        return ResponseEntity.ok(episodes);
    }
}
