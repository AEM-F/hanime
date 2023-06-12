package nl.fhict.hanimediscovery.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Episode {
    private Long id;
    private Long animeId;
    private Integer season;
    private LocalDateTime releaseDate;
    private List<Url> episodeUrls;
}
