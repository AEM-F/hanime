package nl.fhict.hanimediscovery.repositories;

import nl.fhict.hanimediscovery.domain.entities.ContentRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRelationRepository extends JpaRepository<ContentRelation, Long> {
    // Add custom query methods if needed
}

