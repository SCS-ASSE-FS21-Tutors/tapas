package ch.unisg.tapasrobotexecutor.robotexecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TapasRobotExecutorApplication {

	public static void main(String[] args) {
		SpringApplication tapasRobotExecutor = new SpringApplication(TapasRobotExecutorApplication.class);
		tapasRobotExecutor.run(args);
	}

}
