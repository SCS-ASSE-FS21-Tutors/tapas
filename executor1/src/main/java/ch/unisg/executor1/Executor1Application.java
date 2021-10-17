package ch.unisg.executor1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.unisg.executor1.executor.domain.Executor;

@SpringBootApplication
public class Executor1Application {

	public static void main(String[] args) {
		SpringApplication.run(Executor1Application.class, args);
		Executor.getExecutor();
	}

}
