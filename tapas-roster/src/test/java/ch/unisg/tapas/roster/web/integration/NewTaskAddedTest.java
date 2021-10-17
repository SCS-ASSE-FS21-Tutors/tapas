package ch.unisg.tapas.roster.web.integration;

import ch.unisg.tapas.roster.entities.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureWireMock(port=9003)
@SpringBootTest(properties = {"ch.unisg.tapas.executor-pool-url=http://localhost:9003"})
public class NewTaskAddedTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() throws Exception {

        // Set up mocked executor-pool with Wiremock
        stubFor(WireMock.post(urlEqualTo("/assignment/"))
                .willReturn(aResponse()
                        .withStatus(201)));
    }


    @Test
    public void testAddNewTask() throws Exception {

        // ARRANGE
        var task = new Task(
                new Task.TaskName("Somename"),
                new Task.TaskType("sometype"));

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(task);

        // ACT
        mockMvc.perform(
                post("/roster/newtask/")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk());

        // ASSERT
        verify(postRequestedFor(urlEqualTo("/assignment/"))
                .withHeader("Content-Type", equalTo("application/json")));

    }

}
