package ch.unisg.tapasexecutor;

import ch.unisg.tapasbase.TapasMicroservice;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;

@TapasMicroservice
@SpringBootApplication
@EnableAsync
public class RobotApplication {

	public static void main(String[] args) {
		SpringApplication RobotApp = new SpringApplication(RobotApplication.class);
		RobotApp.run();
	}

	@Bean(name = "threadPoolTaskExecutor")
	public Executor threadPoolTaskExecutor() {
		return new ThreadPoolTaskExecutor();
	}
}
