package ch.unisg.executorrobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.unisg.executorrobot.executor.domain.Executor;

@SpringBootApplication
public class ExecutorrobotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExecutorrobotApplication.class, args);
		Executor.getExecutor();
	}

}
