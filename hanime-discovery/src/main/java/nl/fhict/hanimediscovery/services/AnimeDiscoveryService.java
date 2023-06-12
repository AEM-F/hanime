package nl.fhict.hanimediscovery.services;

import lombok.AllArgsConstructor;
import nl.fhict.hanimediscovery.domain.entities.Anime;
import nl.fhict.hanimediscovery.domain.entities.Genre;
import nl.fhict.hanimediscovery.repositories.IAnimeRepository;
import nl.fhict.hanimediscovery.web.client.AnimeDeleteEventProducer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AnimeDiscoveryService implements IAnimeDiscoveryService{
    private IAnimeRepository animeRepository;
    private AnimeDeleteEventProducer eventProducer;

    @Override
    public Page<Anime> getAllAnimes(PageRequest pageRequest) {
        return animeRepository.findAll(pageRequest);
    }

    @Override
    public Anime createAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    @Override
    public Anime getAnimeById(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Anime not found"));
    }

    @Override
    public Anime updateAnime(Long id, Anime updatedAnime) {
        Anime existingAnime = getAnimeById(id);

        // Update the fields that are not associated with CascadeType.DETACH
        existingAnime.setMalId(updatedAnime.getMalId());
        existingAnime.setIsUpcoming(updatedAnime.getIsUpcoming());
        existingAnime.setImageAnime(updatedAnime.getImageAnime());
        existingAnime.setGenres(updatedAnime.getGenres());
        existingAnime.setAnimeYear(updatedAnime.getAnimeYear());
        existingAnime.setAnimeSeason(updatedAnime.getAnimeSeason());
        existingAnime.setAnimeSynopsis(updatedAnime.getAnimeSynopsis());
        existingAnime.setMalScore(updatedAnime.getMalScore());
        existingAnime.setAnimeRating(updatedAnime.getAnimeRating());
        existingAnime.setAnimeDuration(updatedAnime.getAnimeDuration());
        existingAnime.setAiredFrom(updatedAnime.getAiredFrom());
        existingAnime.setAiredTo(updatedAnime.getAiredTo());
        existingAnime.setIsAiring(updatedAnime.getIsAiring());
        existingAnime.setAnimeStatus(updatedAnime.getAnimeStatus());
        existingAnime.setTotalEpNr(updatedAnime.getTotalEpNr());
        existingAnime.setTitleEnglish(updatedAnime.getTitleEnglish());
        existingAnime.setTitleJapanese(updatedAnime.getTitleJapanese());
        existingAnime.setAnimeType(updatedAnime.getAnimeType());

        // Save the updated anime
        return animeRepository.save(existingAnime);
    }

    @Override
    public void deleteAnime(Long id) {
        Anime anime = getAnimeById(id);
        animeRepository.delete(anime);
        // Send message to episodes microservice to delete associated episodes
        eventProducer.sendAnimeDeletedEvent(id);
    }

    @Override
    public Page<Anime> getAnimesByCriteria(
            List<Genre> genres,
            Boolean isUpcoming,
            String animeYear,
            String animeSeason,
            String animeSynopsis,
            LocalDateTime airedFrom,
            LocalDateTime airedTo,
            String animeStatus,
            String animeType,
            String titleEnglish,
            String titleJapanese,
            Pageable pageable) {

        return animeRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Add optional criteria based on provided values
            if (genres != null && !genres.isEmpty()) {
                predicates.add(root.join("genres").in(genres));
            }
            if (isUpcoming != null) {
                predicates.add(builder.equal(root.get("isUpcoming"), isUpcoming));
            }
            if (animeYear != null) {
                predicates.add(builder.equal(root.get("animeYear"), animeYear));
            }
            if (animeSeason != null) {
                predicates.add(builder.equal(root.get("animeSeason"), animeSeason));
            }
            if (animeSynopsis != null) {
                predicates.add(builder.like(root.get("animeSynopsis"), "%" + animeSynopsis + "%"));
            }
            if (airedFrom != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("airedFrom"), airedFrom));
            }
            if (airedTo != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("airedTo"), airedTo));
            }
            if (animeStatus != null) {
                predicates.add(builder.equal(root.get("animeStatus"), animeStatus));
            }
            if (animeType != null) {
                predicates.add(builder.equal(root.get("animeType"), animeType));
            }
            if (titleEnglish != null) {
                predicates.add(builder.like(builder.lower(root.get("titleEnglish")), "%" + titleEnglish.toLowerCase() + "%"));
            }
            if (titleJapanese != null) {
                predicates.add(builder.like(builder.lower(root.get("titleJapanese")), "%" + titleJapanese.toLowerCase() + "%"));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

}
