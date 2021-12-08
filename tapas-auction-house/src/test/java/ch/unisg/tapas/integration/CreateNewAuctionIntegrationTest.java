package ch.unisg.tapas.integration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class CreateNewAuctionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void AssignAndCompleteNewTaskTest() throws Exception{

        // ARRANGE
        String requestBody = "{\n" +
            "  \"taskId\": \"string\",\n" +
            "  \"taskName\": \"string\",\n" +
            "  \"taskType\": \"string\",\n" +
            "  \"taskStatus\": \"ASSIGNED\",\n" +
            "  \"originalTaskUri\": \"string\",\n" +
            "  \"serviceProvider\": \"string\",\n" +
            "  \"inputData\": \"string\",\n" +
            "  \"outputData\": \"string\"\n" +
            "}";

        // ACT
        mockMvc.perform(post("/internal/create-auction-for-task/")
            .header("Content-Type", "application/task+json")
            .content(requestBody))
            .andExpect(status().isAccepted());
    }
}
