package nl.fhict.hanimediscovery.web.client;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AnimeDeleteEventProducer {
    private final RabbitMessagingTemplate rabbitMessagingTemplate;

    public void sendAnimeDeletedEvent(Long animeId) {
        rabbitMessagingTemplate.convertAndSend("anime-deleted-queue", animeId);
    }
}
