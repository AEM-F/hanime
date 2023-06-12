package nl.fhict.hanimediscovery.repositories;

import nl.fhict.hanimediscovery.domain.entities.Anime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface IAnimeRepository extends JpaRepository<Anime, Long>, JpaSpecificationExecutor<Anime> {
    Page<Anime> findAll(Pageable pageable);
}
