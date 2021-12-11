package ch.unisg.tapasexecutor;

import ch.unisg.tapasbase.TapasMicroservice;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@TapasMicroservice
@SpringBootApplication
@EnableAsync
public class RobotExecutorApplication {

    public static void main(String[] args) {
        SpringApplication RobotApp = new SpringApplication(RobotExecutorApplication.class);
        RobotApp.run();
    }

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Primary
    @Bean
    public OpenAPI robotOpenAPI() {

        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Executor 1 - Group 3")
                        .description("<a href=\"https://interactions.ics.unisg.ch/61-102/cam1/live-stream\" target=\"_blank\">Robot Live-Stream</a>"));
    }
}
