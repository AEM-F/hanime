package nl.fhict.hanimeplayback.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.fhict.hanimeplayback.entities.Url;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestEpisode {
    @NotNull(message = "Anime id is required")
    private Long animeId;

    @NotNull(message = "Release date is required")
    private LocalDateTime releaseDate;

    @NotNull(message = "Episode season nr required")
    private Integer seasonNr;

    @NotNull(message = "Episode URLs are required")
    @Size(min = 1, message = "At least one episode URL is required")
    private List<Url> episodeUrls;
}

