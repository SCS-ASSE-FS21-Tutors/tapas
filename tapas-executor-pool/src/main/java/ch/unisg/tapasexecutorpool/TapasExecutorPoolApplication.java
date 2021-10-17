package ch.unisg.tapasexecutorpool;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Executor-Pool-API", version = "1"))
@SpringBootApplication
public class TapasExecutorPoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(TapasExecutorPoolApplication.class, args);
	}

}
