package ch.unisg.executorcomputation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.unisg.executorcomputation.executor.domain.Executor;

@SpringBootApplication
public class ExecutorcomputationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExecutorcomputationApplication.class, args);
		Executor.getExecutor();
	}

}
