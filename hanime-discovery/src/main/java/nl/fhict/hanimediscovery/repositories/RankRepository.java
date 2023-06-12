package nl.fhict.hanimediscovery.repositories;

import nl.fhict.hanimediscovery.domain.entities.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank, Long> {
    // Add custom query methods if needed
}

