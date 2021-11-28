package ch.unisg.tapasexecutor;

import ch.unisg.tapasbase.TapasMicroservice;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@TapasMicroservice
@SpringBootApplication
public class RobotApplication {

	public static void main(String[] args) {
		SpringApplication RobotApp = new SpringApplication(RobotApplication.class);
		RobotApp.run();
	}

}
