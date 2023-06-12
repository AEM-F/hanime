package nl.fhict.hanimediscovery.services;

import nl.fhict.hanimediscovery.domain.entities.ContentRelation;

import java.util.List;

public interface IContentRelationService {
    ContentRelation createContentRelation(ContentRelation contentRelation);

    ContentRelation getContentRelationById(Long id);

    List<ContentRelation> getAllContentRelations();

    ContentRelation updateContentRelation(ContentRelation contentRelation);

    void deleteContentRelation(Long id);
}
