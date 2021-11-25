package ch.unisg.tapasexecutorpool;

import ch.unisg.tapasbase.TapasMicroservice;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@TapasMicroservice
@SpringBootApplication
@EnableScheduling
public class TapasExecutorPoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(TapasExecutorPoolApplication.class, args);
	}

}
