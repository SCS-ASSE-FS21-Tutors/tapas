package ch.unisg.tapastasks;

import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapastasks.tasks.application.port.out.AddTaskPort;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.BDDAssertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddNewTaskToTaskListSystemTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AddTaskPort addTaskPort;

    @Test
    void addNewTaskToTaskList() throws JSONException {

        Task.TaskName taskName = new Task.TaskName("system-integration-test-task");
        Task.TaskType taskType = new Task.TaskType("system-integration-test-type");
        Task.OriginalTaskUri originalTaskUri = new Task.OriginalTaskUri("example.org");

        ResponseEntity response = whenAddNewTaskToEmptyList(taskName, taskType, originalTaskUri);

        JSONObject responseJson = new JSONObject(response.getBody().toString());
        String respTaskId = responseJson.getString("taskId");
        String respTaskName = responseJson.getString("taskName");
        String respTaskType = responseJson.getString("taskType");

        then(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(respTaskId).isNotEmpty();
        then(respTaskName).isEqualTo(taskName.getValue());
        then(respTaskType).isEqualTo(taskType.getValue());
        then(TaskList.getTapasTaskList().getListOfTasks().getValue()).hasSize(1);

    }

    private ResponseEntity whenAddNewTaskToEmptyList(
        Task.TaskName taskName,
        Task.TaskType taskType,
        Task.OriginalTaskUri originalTaskUri) throws JSONException {

            TaskList.getTapasTaskList().getListOfTasks().getValue().clear();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", TaskJsonRepresentation.MEDIA_TYPE);

            String jsonPayLoad = new JSONObject()
                .put("taskName", taskName.getValue() )
                .put("taskType", taskType.getValue())
                .put("originalTaskUri",originalTaskUri.getValue())
                .toString();

            HttpEntity<String> request = new HttpEntity<>(jsonPayLoad,headers);

            return restTemplate.exchange(
                "/tasks/",
                HttpMethod.POST,
                request,
                Object.class
            );

    }

}
