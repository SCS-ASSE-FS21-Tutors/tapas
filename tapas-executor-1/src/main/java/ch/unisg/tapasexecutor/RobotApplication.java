package ch.unisg.tapasexecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RobotApplication {

	public static void main(String[] args) {
		SpringApplication RobotApp = new SpringApplication(RobotApplication.class);
		RobotApp.run();
	}

}
