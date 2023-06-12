package nl.fhict.hanimeplayback.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long animeId;
    private LocalDateTime releaseDate;
    private Integer season;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Url> episodeUrls;
}
