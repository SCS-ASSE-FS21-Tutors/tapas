package ch.unisg.tapas.integration;

import ch.unisg.tapas.auctionhouse.application.port.in.LaunchAuctionCommand;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UniformApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testNewAuction() throws Exception{

        String requestBody = "{\n" +
            "  \"auctionId\": \"1\",\n" +
            "  \"auctionHouseUri\": \"https://127.0.0.1:8085/\",\n" +
            "  \"taskUri\":\"http://example.org/tasks/cef2fa9d-367b-4e7f-bf06-3b1fea35f354\",\n" +
            "  \"taskType\":\"COMPUTATION\",\n" +
            "  \"deadline\":\"2021-12-24 12:00:00\"\n" +
            "}";

        // ACT
        mockMvc.perform(post("/auction")
            .header("Content-Type", "application/auction+json")
            .content(requestBody))
            .andExpect(status().is2xxSuccessful());
    }

    /*
    @Test
    public void testNewBid() throws Exception{

        // Arrange
        String requestBody = "{\n" +
            "  \"auctionId\":\"1\",\n" +
            "  \"bidderName\":\"Group1\",\n" +
            "  \"bidderAuctionHouseUri\":\"http://example.com/\",\n" +
            "  \"bidderTaskListUri\":\"http://example.com/tasks/\"\n" +
            "}";

        // ACT
        mockMvc.perform(post("/bid")
            .header("Content-Type", "application/bid+json")
            .content(requestBody))
            .andExpect(status().is2xxSuccessful());
    }
    */

    /*
    @Test
    public void testNewTaskwinner() throws Exception{

        String requestBody = "{\n" +
            "  \"taskId\":\"cef2fa9d-367b-4e7f-bf06-3b1fea35f354\",\n" +
            "  \"taskName\":\"task1\",\n" +
            "  \"taskType\":\"COMPUTATION\",\n" +
            "  \"taskStatus\":\"ASSIGNED\",\n" +
            "  \"originalTaskUri\":\"http://example.org/tasks/cef2fa9d-367b-4e7f-bf06-3b1fea35f354\",\n" +
            "  \"serviceProvider\":\"tapas-group1\",\n" +
            "  \"inputData\":\"1+1\",\n" +
            "  \"outputData\":\"2\"\n" +
            "}";

        // ACT
        mockMvc.perform(post("/taskwinner")
            .header("Content-Type", "application/task+json")
            .content(requestBody))
            .andExpect(status().isAccepted());
    }
    */

    @Test
    public void testTaskCompleted() throws Exception{

        String requestBody = "{\n" +
            "  \"taskId\":\"cef2fa9d-367b-4e7f-bf06-3b1fea35f354\",\n" +
            "  \"taskName\":\"task1\",\n" +
            "  \"taskType\":\"COMPUTATION\",\n" +
            "  \"taskStatus\":\"EXECUTED\",\n" +
            "  \"originalTaskUri\":\"http://example.org/tasks/cef2fa9d-367b-4e7f-bf06-3b1fea35f354\",\n" +
            "  \"serviceProvider\":\"tapas-group1\",\n" +
            "  \"inputData\":\"1+1\",\n" +
            "  \"outputData\":\"2\"\n" +
            "}";

        // ACT
        mockMvc.perform(post("/tasks/cef2fa9d-367b-4e7f-bf06-3b1fea35f354")
            .header("Content-Type", "application/json-patch+json")
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().json(requestBody));
    }
}
