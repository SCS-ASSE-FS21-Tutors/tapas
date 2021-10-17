package ch.unisg.tapasexecutorpool.integration;

import ch.unisg.tapasexecutorpool.pool.adapter.out.repository.MockExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class AssignNewTaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockExecutorRepository repository;

    private Task newTask = new Task(
            new Task.TaskId("someid"),
            new Task.TaskName("somename"),
            new Task.TaskType("sometype")
    );

    @BeforeEach
    public void setUp(){

        repository.deleteAll();
    }

    @Test
    public void AssignNewTaskTestNoExecutorAvailable() throws Exception{

        // ARRANGE
        // We have no executor registered that can do that
        String requestBody = new ObjectMapper().writeValueAsString(newTask);

        // ACT
        mockMvc.perform(post("/assignment/")
                .header("Content-Type", "application/json")
                .content(requestBody))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void AssignAndCompleteNewTaskTest() throws Exception{

        // ARRANGE
        // We add a new executor that can resolve that task
        Executor executor = new Executor(
                new Executor.ExecutorName("ExecName"),
                new Executor.ExecutorType("sometype"),
                new Executor.ExecutorUrl("someurl"));
        repository.addExecutor(executor);

        String requestBody = new ObjectMapper().writeValueAsString(newTask);

        // ACT
        mockMvc.perform(post("/assignment/")
                .header("Content-Type", "application/json")
                .content(requestBody))
                .andExpect(status().isCreated());

        assertNotNull(executor.getAssignedTask());
        assertEquals(newTask.getTaskId(), executor.getAssignedTask().getTaskId());
        assertEquals(Executor.State.OCCUPIED, executor.getExecutorState().getValue());


        // Also test task can be completed
        mockMvc.perform(put("/completion/")
                .param("taskId", newTask.getTaskId().getValue()))
                .andExpect(status().isOk());

        assertEquals(null, executor.getAssignedTask());
        assertEquals(Executor.State.AVAILABLE, executor.getExecutorState().getValue());
    }
}
