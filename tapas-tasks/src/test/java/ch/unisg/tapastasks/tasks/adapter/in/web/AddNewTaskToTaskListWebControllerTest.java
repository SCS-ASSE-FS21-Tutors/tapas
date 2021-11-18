package ch.unisg.tapastasks.tasks.adapter.in.web;

import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapastasks.tasks.adapter.in.web.AddNewTaskToTaskListWebController;
import ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb.TaskRepository;
import ch.unisg.tapastasks.tasks.application.port.in.AddNewTaskToTaskListCommand;
import ch.unisg.tapastasks.tasks.application.port.in.AddNewTaskToTaskListUseCase;
import ch.unisg.tapastasks.tasks.domain.Task;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AddNewTaskToTaskListWebController.class)
public class AddNewTaskToTaskListWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddNewTaskToTaskListUseCase addNewTaskToTaskListUseCase;

    @MockBean
    TaskRepository taskRepository;

    @Disabled
    @Test
    void testAddNewTaskToTaskList() throws Exception {

        String taskName = "test-request";
        String taskType = "test-request-type";
        String originalTaskUri = "example.org";

        String jsonPayLoad = new JSONObject()
            .put("taskName", taskName )
            .put("taskType", taskType)
            .put("originalTaskUri",originalTaskUri)
            .toString();

        //This raises a NullPointerException since it tries to build the HTTP response with attributes from
        //the domain object (created task), which is mocked --> we need System Tests here!
        //See the buckpal example from the lecture for a working integration test for testing the web controller
        mockMvc.perform(post("/tasks/")
                .contentType(TaskJsonRepresentation.MEDIA_TYPE)
                .content(jsonPayLoad))
                .andExpect(status().isCreated());

        then(addNewTaskToTaskListUseCase).should()
            .addNewTaskToTaskList(eq(new AddNewTaskToTaskListCommand(
                new Task.TaskName(taskName), new Task.TaskType(taskType),
                Optional.of(new Task.OriginalTaskUri(originalTaskUri))
            )));

    }


}
