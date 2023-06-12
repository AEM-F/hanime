package nl.fhict.hanimediscovery.services;

import nl.fhict.hanimediscovery.domain.entities.Rank;

import java.util.List;

public interface IRankService {
    Rank createRank(Rank rank);

    List<Rank> getAllRanks();

    Rank updateRank(Rank rank);

    void deleteRank(Long id);
}
