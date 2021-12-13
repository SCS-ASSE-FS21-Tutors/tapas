package ch.unisg.tapastasks.integration;

import ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb.TaskRepository;
import ch.unisg.tapastasks.tasks.application.port.out.NewTaskAddedEventPort;
import ch.unisg.tapastasks.tasks.domain.Task;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AddNewTaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TaskRepository taskRepository;

    @MockBean
    NewTaskAddedEventPort newTaskAddedEventPort;

    @Test
	void testAddNewTask() throws Exception {

        // ARRANGE
        Task.TaskName taskName = new Task.TaskName("system-integration-test-task");
        Task.TaskType taskType = new Task.TaskType("system-integration-test-type");
        Task.InputData inputData = new Task.InputData("test-input-data");

        String requestBody = new JSONObject()
            .put("taskName", taskName.getValue() )
            .put("taskType", taskType.getValue())
            .put("inputData",inputData.getValue())
            .toString();

        // ACT
        mockMvc.perform(post("/tasks/")
                .contentType("application/json")
                .content(requestBody))
            .andExpect(status().isCreated());

        // Assert
        Mockito.verify(newTaskAddedEventPort).publishNewTaskAddedEvent(any());
        Mockito.verify(taskRepository).save(any());
	}

}
