package ch.unisg.tapasexecutorpool.integration;

import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class AddNewExecutorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExecutorRepository repository;

    @Test
    public void AddNewExectorTest() throws Exception{

        // ARRANGE
        Executor executor = new Executor(
                new Executor.ExecutorName("Name"),
                new Executor.ExecutorType("Type"),
                new Executor.ExecutorUrl("Url"));

        ObjectMapper om = new ObjectMapper();
        String requestBody = om.writeValueAsString(executor);
        Executor copy = om.readValue(requestBody, Executor.class);

        long countBefore = repository.getExecutors().stream().count();

        // ACT
        mockMvc.perform(
                post("/executors/")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated());

        // Assert
        long countAfter = repository.getExecutors().stream().count();
        assertEquals(1, countAfter - countBefore, "Count of stored executors in not increased by one");
    }
}
