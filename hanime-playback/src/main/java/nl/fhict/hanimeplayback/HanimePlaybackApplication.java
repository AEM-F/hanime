package nl.fhict.hanimeplayback;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RefreshScope
public class HanimePlaybackApplication {

	public static void main(String[] args) {
		SpringApplication.run(HanimePlaybackApplication.class, args);
	}

}
