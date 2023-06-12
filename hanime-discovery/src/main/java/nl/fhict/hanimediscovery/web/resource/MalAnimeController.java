package nl.fhict.hanimediscovery.web.resource;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.fhict.hanimediscovery.services.IMalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api-mal/anime")
public class MalAnimeController {
//    private final IMalService malContentService;
//
//    public ResponseEntity<Object> GetAnimeFullByMalId(Long id){
//
//        return malContentService.GetAnimeFullByMalId(id);
//    }
//    public ResponseEntity<Object> GetAnimeStatisticsMal(Long id){
//
//        return malContentService.GetAnimeStatisticsMal(id);
//    }
//    public ResponseEntity<Object> GetAnimeSearchMal(Integer page,
//                                                    Integer limit,
//                                                    String query,
//                                                    String type,
//                                                    Long score,
//                                                    String status,
//                                                    String rating,
//                                                    Boolean sfw,
//                                                    String genres,
//                                                    String startDate,
//                                                    String endDate){
//        return malContentService.GetAnimeSearchMal(page, limit, query, type, score, status, rating, sfw, genres, startDate, endDate);
//    }
//    public ResponseEntity<Object> GetTopAnimeMal(String type, String filter, Integer page, Integer limit){
//
//        return malContentService.GetTopAnimeMal(type, filter, page, limit);
//    }
//    public ResponseEntity<Object> GetSchedules(Integer page, String filter, String sfw, String limit){
//
//        return malContentService.GetSchedules(page, filter, sfw, limit);
//    }
}
