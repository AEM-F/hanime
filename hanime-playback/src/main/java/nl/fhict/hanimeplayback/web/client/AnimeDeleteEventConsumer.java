package nl.fhict.hanimeplayback.web.client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.fhict.hanimeplayback.services.IEpisodeService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class AnimeDeleteEventConsumer {
    private final IEpisodeService episodeService;

    @RabbitListener(queues = "anime-deleted-queue")
    public void handleAnimeDeletedEvent(Long animeId) {
        try {
            // Delete episodes associated with the animeId
            episodeService.deleteEpisodeByAnimeId(animeId);
        } catch (Exception e) {
            // Handle the exception
            // You can log the error, perform any necessary cleanup, or take alternative actions
            // For example, you can send a notification or retry the deletion later
            log.error("Error occurred while deleting episodes for animeId: " + animeId);
            e.printStackTrace();
        }
    }
}
