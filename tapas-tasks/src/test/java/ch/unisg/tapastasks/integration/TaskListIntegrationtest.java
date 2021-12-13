package ch.unisg.tapastasks.integration;

import ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb.TaskRepository;
import ch.unisg.tapastasks.tasks.application.port.out.LoadTaskListPort;
import ch.unisg.tapastasks.tasks.application.port.out.LoadTaskPort;
import ch.unisg.tapastasks.tasks.application.port.out.NewTaskAddedEventPort;
import ch.unisg.tapastasks.tasks.application.port.out.UpdateTaskPort;
import ch.unisg.tapastasks.tasks.application.service.AddNewTaskToTaskListService;
import ch.unisg.tapastasks.tasks.domain.Task;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class TaskListIntegrationtest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LoadTaskListPort loadTaskListPort;

    @MockBean
    LoadTaskPort loadTaskPort;

    @MockBean
    UpdateTaskPort updateTaskPort;

    @MockBean
    TaskRepository taskRepository;

    @MockBean
    NewTaskAddedEventPort newTaskAddedEventPort;

    @MockBean
    AddNewTaskToTaskListService addNewTaskToTaskListService;

    @Test
    public void testGetTaskList() throws Exception {

        Mockito.when(loadTaskListPort.loadTaskList()).thenReturn(List.of(
            new Task(
                new Task.TaskId("id1"),
                new Task.TaskName("Sometaskname"),
                new Task.TaskType("Sometype"),
                new Task.OriginalTaskUri("http://localhost"),
                new Task.TaskStatus(Task.Status.ASSIGNED),
                new Task.ServiceProvider("Service Provider"),
                new Task.InputData("Input Data"),
                null),
            new Task(
                new Task.TaskId("id2"),
                new Task.TaskName("Some other name"),
                new Task.TaskType("Sometype"),
                new Task.OriginalTaskUri("http://localhost"),
                new Task.TaskStatus(Task.Status.EXECUTED),
                new Task.ServiceProvider("Service Provider"),
                new Task.InputData("Input Data"),
                new Task.OutputData("Output Data"))));

        mockMvc.perform(get("/tasklist"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetTaskListEmptyList() throws Exception {

        Mockito.when(loadTaskListPort.loadTaskList()).thenReturn(List.of());

        mockMvc.perform(get("/tasklist"))
            .andExpect(status().isOk());
    }
}
