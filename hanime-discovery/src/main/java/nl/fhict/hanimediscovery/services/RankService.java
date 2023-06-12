package nl.fhict.hanimediscovery.services;

import lombok.AllArgsConstructor;
import nl.fhict.hanimediscovery.domain.entities.Rank;
import nl.fhict.hanimediscovery.repositories.RankRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class RankService implements IRankService {
    private final RankRepository rankRepository;

    @Override
    public Rank createRank(Rank rank) {
        return rankRepository.save(rank);
    }

    public Rank getRankById(Long id) {
        return rankRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rank not found"));
    }

    @Override
    public List<Rank> getAllRanks() {
        return rankRepository.findAll();
    }

    @Override
    public Rank updateRank(Rank rank) {
        if (!rankRepository.existsById(rank.getId())) {
            throw new NoSuchElementException("Rank not found");
        }
        return rankRepository.save(rank);
    }

    @Override
    public void deleteRank(Long id) {
        if (!rankRepository.existsById(id)) {
            throw new NoSuchElementException("Rank not found");
        }
        rankRepository.deleteById(id);
    }
}
