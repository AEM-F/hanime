package nl.fhict.hanimediscovery.web.client;

import nl.fhict.hanimediscovery.domain.entities.Episode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("playback")
public interface EpisodeFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/episode/anime/{id}", consumes = "application/json")
    List<Episode> getAllEpisodesByAnimeId(@RequestHeader("hanime-correlation-id") String correlationid, @PathVariable Long id);
}
