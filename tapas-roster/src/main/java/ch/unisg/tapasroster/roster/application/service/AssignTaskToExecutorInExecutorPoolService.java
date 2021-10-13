package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorInExecutorPoolCommand;
import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorInExecutorPoolUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
@Component
@Transactional
public class AssignTaskToExecutorInExecutorPoolService implements AssignTaskToExecutorInExecutorPoolUseCase {


    @Override
    public boolean assignTaskToExecutor(AssignTaskToExecutorInExecutorPoolCommand command) {
        System.out.println("It works");
        // IN:Task from TaskList
        System.out.println("TaskId:" + command.getTaskId().getValue());
        System.out.println("TaskType:" + command.getTaskType().getValue());

        String typeOfNewTask = command.getTaskType().getValue();
        // get executors via port
        String executorPoolURL = "http://localhost:8083";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(executorPoolURL+"/executors/"))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            response.
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        // find matching type (task==executor)

        // send request to executor for execution

        //OUT: Later will be current status of the TaskList
        return false;
    }
}