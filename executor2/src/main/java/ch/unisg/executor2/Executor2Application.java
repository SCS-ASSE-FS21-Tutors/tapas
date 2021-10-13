package ch.unisg.executor2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.unisg.executor2.executor.domain.Executor;

@SpringBootApplication
public class Executor2Application {

	public static void main(String[] args) {
		SpringApplication.run(Executor2Application.class, args);
		Executor.getExecutor();
	}

}
