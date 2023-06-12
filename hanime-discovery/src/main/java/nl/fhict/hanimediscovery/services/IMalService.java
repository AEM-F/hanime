package nl.fhict.hanimediscovery.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IMalService {
    ResponseEntity<Object> GetAnimeFullByMalId(Long id);
    ResponseEntity<Object> GetAnimeStatisticsMal(Long id);
    ResponseEntity<Object> GetAnimeSearchMal(Integer page,
                                             Integer limit,
                                             String query,
                                             String type,
                                             Long score,
                                             String status,
                                             String rating,
                                             Boolean sfw,
                                             String genres,
                                             String startDate,
                                             String endDate);
    ResponseEntity<Object> GetTopAnimeMal(String type, String filter, Integer page, Integer limit);
    ResponseEntity<Object> GetSchedules(Integer page, String filter, String sfw, String limit);
}
