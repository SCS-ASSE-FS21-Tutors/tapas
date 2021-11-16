package ch.unisg.tapasexecutorpool.integration;

import ch.unisg.tapasexecutorpool.pool.adapter.out.repository.InternalExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.application.service.AssignTaskService;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class AssignNewTaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InternalExecutorRepository repository;

    @Autowired
    private AssignTaskService assignTaskService;

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
        mockMvc.perform(post("/execute/")
                .header("Content-Type", "application/json")
                .content(requestBody))
                .andExpect(status().isAccepted());

        Task taskFromQueue = assignTaskService.taskQueue.peek();
        assertEquals(newTask.getTaskId(), taskFromQueue.getTaskId());
    }

    @Test
    public void CanExecuteIntergrationTest() throws Exception{

        // ARRANGE
        // We add a new executor that can resolve that task
        Executor executor = new Executor(
                new Executor.ExecutorName("ExecName"),
                new Executor.ExecutorType("sometype"),
                new Executor.ExecutorUrl("someurl"));
        repository.addExecutor(executor);

        String requestBody = new ObjectMapper().writeValueAsString(newTask);

        // ACT
        mockMvc.perform(post("/can-execute/")
                .header("Content-Type", "application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json("{'executable':true}"));

        Task otherTask = new Task(
                new Task.TaskId("someid"),
                new Task.TaskName("somename"),
                new Task.TaskType("sometypeOTHERTYPE")
        );
        String requestBody2 = new ObjectMapper().writeValueAsString(otherTask);

        // ACT
        mockMvc.perform(post("/can-execute/")
                .header("Content-Type", "application/json")
                .content(requestBody2))
                .andExpect(status().isOk())
                .andExpect(content().json("{'executable':false}"));
    }
}
