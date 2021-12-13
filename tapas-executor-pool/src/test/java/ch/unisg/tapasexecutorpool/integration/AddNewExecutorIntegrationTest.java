package ch.unisg.tapasexecutorpool.integration;

import ch.unisg.tapasexecutorpool.common.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.common.formats.NewExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.pool.adapter.out.persistence.mongodb.ExecutorPersistenceAdapter;
import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    ExecutorPersistenceAdapter persistenceAdapter;

    @Test
    public void AddNewExecutorTest() throws Exception{

        // ARRANGE
        NewExecutorJsonRepresentation newExecutorJsonRepresentation = new NewExecutorJsonRepresentation(
                "Name",
                "Type",
                "Url");

        ObjectMapper om = new ObjectMapper();
        String requestBody = om.writeValueAsString(newExecutorJsonRepresentation);

        long countBefore = repository.getExecutors().size();

        // ACT
        mockMvc.perform(
                post("/executors/")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated());

        // Assert
        long countAfter = repository.getExecutors().size();
        assertEquals(1, countAfter - countBefore, "Count of stored executors in not increased by one");
    }
}
