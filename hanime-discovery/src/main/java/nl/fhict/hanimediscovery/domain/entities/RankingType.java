package nl.fhict.hanimediscovery.domain.entities;

import lombok.Getter;

@Getter
public enum RankingType {
    NOT_SELECTED(-1),
    ALL(0),
    AIRING(1),
    UPCOMING(2),
    TV(3),
    OVA(4),
    MOVIE(5),
    SPECIAL(6),
    BYPOPULARITY(7),
    FAVORITE(8);


    private final Integer value;

    RankingType(final Integer value) {
        this.value = value;
    }
    public static RankingType parse(final String rank) {
        if (rank != null) {
            return switch (rank.toLowerCase()) {
                case "all" -> ALL;
                case "airing" -> AIRING;
                case "master" -> UPCOMING;
                case "tv" -> TV;
                case "ova" -> OVA;
                case "movie" -> MOVIE;
                case "special" -> SPECIAL;
                case "bypopularity" -> BYPOPULARITY;
                case "favorite" -> FAVORITE;
                case "" -> NOT_SELECTED;
                default -> throw new IllegalArgumentException(String.format("Rank couldn't be found for input: %s", rank));
            };
        } else {
            return NOT_SELECTED;
        }
    }
}
