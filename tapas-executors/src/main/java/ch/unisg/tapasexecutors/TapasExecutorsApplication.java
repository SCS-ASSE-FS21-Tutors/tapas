package ch.unisg.tapasexecutors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TapasExecutorsApplication {

	public static void main(String[] args) {
		// SpringApplication.run(TapasExecutorsApplication.class, args);
		SpringApplication tapasExecutorsApp = new SpringApplication(TapasExecutorsApplication.class);
		tapasExecutorsApp.run(args);
	}
}