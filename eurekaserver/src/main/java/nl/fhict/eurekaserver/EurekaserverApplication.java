package nl.fhict.eurekaserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaServer
@EnableRabbit
@Slf4j
public class EurekaserverApplication implements InitializingBean {

	@Value("${spring.rabbitmq.host}")
	private String rabbitHost;

	@Value("${spring.rabbitmq.port}")
	private int rabbitPort;

	@Value("${spring.rabbitmq.username}")
	private String rabbitUsername;

	@Value("${spring.rabbitmq.password}")
	private String rabbitPassword;


	public static void main(String[] args) {
		SpringApplication.run(EurekaserverApplication.class, args);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost(rabbitHost);
		connectionFactory.setPort(rabbitPort);
		connectionFactory.setUsername(rabbitUsername);
		connectionFactory.setPassword(rabbitPassword);
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		Queue animeDeletedQueue = new Queue("anime-deleted-queue", true);
		rabbitTemplate.execute(channel -> {
			channel.queueDeclare(animeDeletedQueue.getName(), true, false, false, null);
			return null;
		});
		log.info("Queue initialized: anime-deleted-queue");
	}

}
