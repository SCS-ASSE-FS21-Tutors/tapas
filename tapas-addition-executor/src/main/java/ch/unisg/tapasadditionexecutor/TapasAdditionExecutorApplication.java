package ch.unisg.tapasadditionexecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TapasAdditionExecutorApplication {

	public static void main(String[] args) {
		SpringApplication tapasAdditionExecutor = new SpringApplication(TapasAdditionExecutorApplication.class);
		tapasAdditionExecutor.run(args);
	}

}
