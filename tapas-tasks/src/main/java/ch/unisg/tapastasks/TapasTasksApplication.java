package ch.unisg.tapastasks;

import ch.unisg.tapastasks.tasks.adapter.in.messaging.mqtt.TaskExecutedEventMqttListenerAdapter;
import ch.unisg.tapastasks.tasks.application.handler.TaskExecutedHandler;
import ch.unisg.tapastasks.tasks.application.port.in.TaskExecutedEventHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TapasTasksApplication {

	public static void main(String[] args) {

		SpringApplication tapasTasksApp = new SpringApplication(TapasTasksApplication.class);

        TaskExecutedEventMqttListenerAdapter taskExecutedEventMqttListenerAdapter =
            new TaskExecutedEventMqttListenerAdapter();

        taskExecutedEventMqttListenerAdapter.startReceivingNewTasks();

		tapasTasksApp.run(args);
	}

}
