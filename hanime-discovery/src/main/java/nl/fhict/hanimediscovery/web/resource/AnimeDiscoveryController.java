package nl.fhict.hanimediscovery.web.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.fhict.hanimediscovery.config.DiscoveryServiceConfig;
import nl.fhict.hanimediscovery.domain.entities.Anime;
import nl.fhict.hanimediscovery.domain.entities.Episode;
import nl.fhict.hanimediscovery.domain.entities.Genre;
import nl.fhict.hanimediscovery.services.IAnimeDiscoveryService;
import nl.fhict.hanimediscovery.web.client.EpisodeFeignClient;
import nl.fhict.hanimediscovery.web.dto.Properties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@AllArgsConstructor
@RequestMapping("/api/anime")
@Slf4j
public class AnimeDiscoveryController {
    private DiscoveryServiceConfig discoveryServiceConfig;
    private final IAnimeDiscoveryService animeService;
    private final EpisodeFeignClient episodeFeignClient;

    @CircuitBreaker(name = "animeEpisodesForAnimeContent")
    @Retry(name = "retryAnimeEpisodesForAnimeContent")
    @GetMapping("/{animeId}/episodes")
    //@RateLimiter(name = "animeEpisodesForAnimeContent", fallbackMethod = "getAllEpisodesByAnimeIdFallbackRate")
    public ResponseEntity<List<Episode>> getAllEpisodesByAnimeId(@RequestHeader("hanime-correlation-id") String correlationid,
                                                                 @PathVariable("animeId") Long animeId) throws NoSuchElementException {
        log.info("Action: getting all episodes by anime id: "+ animeId.toString() + ";Correlation id: " + correlationid);
        List<Episode> episodes = episodeFeignClient.getAllEpisodesByAnimeId(correlationid, animeId);
        return ResponseEntity.ok(episodes);
    }

    // fallback method in case of errors from the called service
    private ResponseEntity<Object> getAllEpisodesByAnimeIdFallback(@RequestHeader("hanime-correlation-id") String correlationid,
                                                                   @PathVariable("animeId") Long animeId, Throwable t) throws NoSuchElementException {
        return ResponseEntity.ok().body("Fallback method for the episodes - Circuit breakers");
    }

    // fallback error rate limiter
//    private ResponseEntity<Object> getAllEpisodesByAnimeIdFallbackRate(@RequestHeader("hanime-correlation-id") String correlationid, @PathVariable("animeId") Long animeId, Throwable t) throws NoSuchElementException {
//        return ResponseEntity.badRequest().body("To many requests send, try again later");
//    }

    @GetMapping("/properties")
    public String getPropertiesDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(discoveryServiceConfig.getMsg(), discoveryServiceConfig.getBuildVersion(),
                discoveryServiceConfig.getMailDetails(), discoveryServiceConfig.getActiveBranches());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

    // Create a new anime
    @PostMapping
    @Timed(value = "createAnime.time", description = "Time taken to create anime")
    public ResponseEntity<Anime> createAnime(@RequestBody Anime anime) {
        Anime createdAnime = animeService.createAnime(anime);
        return new ResponseEntity<>(createdAnime, HttpStatus.CREATED);
    }

    // Get all animes with pagination
    @GetMapping
    @Timed(value = "getAllAnimes.time", description = "Time taken to get anime by pagination")
    public ResponseEntity<Page<Anime>> getAllAnimes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Anime> animePage = animeService.getAllAnimes(pageRequest);
        return ResponseEntity.ok(animePage);
    }

    // Get anime by ID
    @GetMapping("/{animeId}")
    @Timed(value = "getAnimeById.time", description = "Time taken to get anime by id")
    public ResponseEntity<Anime> getAnimeById(@PathVariable Long animeId) {
        Anime anime = animeService.getAnimeById(animeId);
        if (anime != null) {
            return ResponseEntity.ok(anime);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update anime by ID
    @PutMapping("/{animeId}")
    @Timed(value = "updateAnime.time", description = "Time taken to update anime")
    public ResponseEntity<Anime> updateAnime(
            @PathVariable Long animeId,
            @RequestBody Anime updatedAnime) {
        Anime updated = animeService.updateAnime(animeId, updatedAnime);
        return ResponseEntity.ok(updated);
    }

    // Delete anime by ID
    @DeleteMapping("/{animeId}")
    @Timed(value = "deleteAnime.time", description = "Time taken to delete anime")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long animeId) {
        Anime anime = animeService.getAnimeById(animeId);
        if (anime != null) {
            animeService.deleteAnime(animeId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    @Timed(value = "searchAnimes.time", description = "Time taken to search anime")
    public ResponseEntity<Page<Anime>> searchAnimes(
            @RequestParam(value = "isUpcoming", required = false) Boolean isUpcoming,
            @RequestParam(value = "genre", required = false) List<Long> genreIds,
            @RequestParam(value = "animeYear", required = false) String animeYear,
            @RequestParam(value = "animeSeason", required = false) String animeSeason,
            @RequestParam(value = "animeSynopsis", required = false) String animeSynopsis,
            @RequestParam(value = "airedFrom", required = false) LocalDateTime airedFrom,
            @RequestParam(value = "airedTo", required = false) LocalDateTime airedTo,
            @RequestParam(value = "animeStatus", required = false) String animeStatus,
            @RequestParam(value = "animeType", required = false) String animeType,
            @RequestParam(value = "titleEnglish", required = false) String titleEnglish,
            @RequestParam(value = "titleJapanese", required = false) String titleJapanese,
            Pageable pageable) {

        List<Genre> genres = null;
        if (genreIds != null && !genreIds.isEmpty()) {
            // Retrieve the Genre objects based on the genreIds from your repository or service
            // Assign the retrieved Genre objects to the 'genres' variable
        }

        Page<Anime> animePage = animeService.getAnimesByCriteria(
                genres,
                isUpcoming,
                animeYear,
                animeSeason,
                animeSynopsis,
                airedFrom,
                airedTo,
                animeStatus,
                animeType,
                titleEnglish,
                titleJapanese,
                pageable);

        return ResponseEntity.ok(animePage);
    }
}
