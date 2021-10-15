package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorInExecutorPoolCommand;
import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorInExecutorPoolUseCase;
import ch.unisg.tapasroster.roster.domain.Executor;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Transactional
public class AssignTaskToExecutorInExecutorPoolService implements AssignTaskToExecutorInExecutorPoolUseCase {


    @Override
    public boolean assignTaskToExecutor(AssignTaskToExecutorInExecutorPoolCommand command) {
        System.out.println("It works");
        // IN:Task from TaskList
        String typeOfNewTask = command.getTaskType().getValue();
        System.out.println("TaskId:" + command.getTaskId().getValue());
        System.out.println("TaskType:" + typeOfNewTask);
        // get executors via port
        String executorPoolURL = "http://localhost:8081";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(executorPoolURL+"/executors/"))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject responseJSON = new JSONObject(response.body());
            System.out.println(response.body());
            JSONArray executorJSONArray = responseJSON.getJSONArray("executorList");

            List<Executor> parsedExecutorList = new ArrayList<>();
            for (int i = 0; i < executorJSONArray.length(); i++) {
                JSONObject currExecutor = executorJSONArray.getJSONObject(i);
                String executorType = currExecutor.getString("executorType");
                String executorUrl = currExecutor.getString("executorUrl");
                Executor executor = new Executor(
                        new Executor.ExecutorUrl(executorUrl),
                        new Executor.ExecutorType(executorType)
                );
                parsedExecutorList.add(executor);
            }

            // find matching type (task==executor)
            for (Executor executor : parsedExecutorList) {
                String executorType = executor.getExecutorType().getValue();
                String executorUrl = executor.getExecutorUrl().getValue();
                if (executorType.equals(typeOfNewTask)) {
                    // send request to executor for execution
                    HttpRequest requestToExecutor = HttpRequest.newBuilder()
                            .uri(URI.create(executorUrl + "/executeTask/"))
                            .GET()
                            .build();
                    response = client.send(requestToExecutor, HttpResponse.BodyHandlers.ofString());
                    System.out.println("Body of message to executor:");
                    System.out.println(response.body());
                    // return that assignment was successful
                    return true;
                }
            }
        } catch (IOException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        //OUT: Later will be current status of the TaskList
        return false;
    }
}