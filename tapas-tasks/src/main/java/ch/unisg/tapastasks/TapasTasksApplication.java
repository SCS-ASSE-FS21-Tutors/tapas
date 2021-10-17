package ch.unisg.tapastasks;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@OpenAPIDefinition(info = @Info(title = "Task-API", version = "1"))
@SpringBootApplication
public class TapasTasksApplication {

	public static void main(String[] args) {

		SpringApplication tapasTasksApp = new SpringApplication(TapasTasksApplication.class);
		tapasTasksApp.run(args);
	}

}
