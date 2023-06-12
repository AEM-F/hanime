package nl.fhict.hanimediscovery.domain.entities;

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
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer malId;
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "rank_anime",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "rank_id"))
    @OrderBy("rankingNumber DESC")
    private List<Rank> rankList;
    private Boolean isUpcoming;
    private String imageAnime;
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "genre_anime",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;
    private String animeYear;
    private String animeSeason;
    private String animeSynopsis;
    private Long malScore;
    private String animeRating;
    private String animeDuration;
    private LocalDateTime airedFrom;
    private LocalDateTime airedTo;
    private Boolean isAiring;
    private String animeStatus;
    private Integer totalEpNr;
    private String titleEnglish;
    private String titleJapanese;
    private String animeType;
    @ManyToMany
    @JoinTable(
            name = "relation_anime",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "relation_id"))
    private List<ContentRelation> animeRelations;
}
