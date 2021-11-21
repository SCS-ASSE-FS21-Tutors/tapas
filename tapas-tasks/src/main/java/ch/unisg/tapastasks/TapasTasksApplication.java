package ch.unisg.tapastasks;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb.TaskRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@OpenAPIDefinition(info = @Info(title = "Task-API", version = "1"))
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = TaskRepository.class)
public class TapasTasksApplication {

	public static void main(String[] args) {
		SpringApplication tapasTasksApp = new SpringApplication(TapasTasksApplication.class);
		tapasTasksApp.run(args);
	}

}
