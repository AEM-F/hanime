package nl.fhict.hanimediscovery.services;

import lombok.AllArgsConstructor;
import nl.fhict.hanimediscovery.domain.entities.ContentRelation;
import nl.fhict.hanimediscovery.repositories.ContentRelationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ContentRelationService implements IContentRelationService {
    private final ContentRelationRepository contentRelationRepository;

    @Override
    public ContentRelation createContentRelation(ContentRelation contentRelation) {
        return contentRelationRepository.save(contentRelation);
    }

    @Override
    public ContentRelation getContentRelationById(Long id) {
        return contentRelationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Content not found"));
    }

    @Override
    public List<ContentRelation> getAllContentRelations() {
        return contentRelationRepository.findAll();
    }

    @Override
    public ContentRelation updateContentRelation(ContentRelation contentRelation) {
        if (!contentRelationRepository.existsById(contentRelation.getId())) {
            throw new NoSuchElementException("ContentRelation not found");
        }
        return contentRelationRepository.save(contentRelation);
    }

    @Override
    public void deleteContentRelation(Long id) {
        if (!contentRelationRepository.existsById(id)) {
            throw new NoSuchElementException("ContentRelation not found");
        }
        contentRelationRepository.deleteById(id);
    }
}

