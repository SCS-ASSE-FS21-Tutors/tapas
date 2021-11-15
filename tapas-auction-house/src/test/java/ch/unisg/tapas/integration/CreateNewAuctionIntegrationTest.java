package ch.unisg.tapas.integration;

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
public class CreateNewAuctionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void AssignAndCompleteNewTaskTest() throws Exception{

        String requestBody = "{\n" +
            "  \"taskId\": {\n" +
            "    \"value\": \"string\"\n" +
            "  },\n" +
            "  \"taskName\": {\n" +
            "    \"value\": \"string\"\n" +
            "  },\n" +
            "  \"taskType\": {\n" +
            "    \"value\": \"string\"\n" +
            "  }\n" +
            "}";

        // ACT
        mockMvc.perform(post("/internal/create-auction-for-task/")
            .header("Content-Type", "application/task+json")
            .content(requestBody))
            .andExpect(status().isAccepted());
    }
}
