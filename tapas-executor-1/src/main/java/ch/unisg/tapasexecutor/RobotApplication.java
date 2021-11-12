package ch.unisg.tapasexecutor;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Executor-1-API", version = "1"))
public class RobotApplication {

	public static void main(String[] args) {
		SpringApplication RobotApp = new SpringApplication(RobotApplication.class);
		RobotApp.run();
	}

}
