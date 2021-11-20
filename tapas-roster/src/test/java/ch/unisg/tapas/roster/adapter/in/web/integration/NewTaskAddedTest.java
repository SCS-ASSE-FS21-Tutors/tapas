package ch.unisg.tapas.roster.adapter.in.web.integration;

import ch.unisg.tapas.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.roster.adapter.out.web.dto.CanExecuteDto;
import ch.unisg.tapas.roster.entities.Task;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureWireMock(port=9003)
@SpringBootTest(properties = {
        "ch.unisg.tapas.executor-pool-url=http://localhost:9003/executor-pool",
        "ch.unisg.tapas.auction-house-url=http://localhost:9003/auction-house"})
public class NewTaskAddedTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddNewTaskShouldGoToExecutorPool() throws Exception {

        // ARRANGE
        // Set up mocked executor-pool with Wiremock
        stubFor(WireMock.post(urlEqualTo("/executor-pool/can-execute/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/task+json")
                        .withBody("{\"executable\":true}")));

        stubFor(WireMock.post(urlEqualTo("/executor-pool/execute?external=false"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.ACCEPTED.value())));


        var task = new Task(
                new Task.TaskName("somename"),
                new Task.TaskType("sometype"));

        String requestBody = TaskJsonRepresentation.serialize(task);

        // ACT
        mockMvc.perform(
                post("/roster/newtask/")
                .contentType("application/task+json")
                .content(requestBody))
                .andExpect(status().isOk());

        // ASSERT
        verify(postRequestedFor(urlEqualTo("/executor-pool/can-execute/"))
                .withHeader("Content-Type", equalTo("application/task+json")));

        verify(postRequestedFor(urlEqualTo("/executor-pool/execute?external=false"))
                .withHeader("Content-Type", equalTo("application/task+json")));
    }

    @Test
    public void testAddNewTaskShouldGoToAuctionHouse() throws Exception {

        // ARRANGE
        // Set up mocked executor-pool with Wiremock
        stubFor(WireMock.post(urlEqualTo("/executor-pool/can-execute/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/task+json")
                        .withBody("{\"executable\":false}")));

        stubFor(WireMock.post(urlEqualTo("/auction-house/internal/create-auction-for-task/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.ACCEPTED.value())));


        var task = new Task(
                new Task.TaskName("somename"),
                new Task.TaskType("sometype"));

        String requestBody = TaskJsonRepresentation.serialize(task);

        // ACT
        mockMvc.perform(
                post("/roster/newtask/")
                        .contentType("application/task+json")
                        .content(requestBody))
                .andExpect(status().isOk());

        // ASSERT
        verify(postRequestedFor(urlEqualTo("/executor-pool/can-execute/"))
                .withHeader("Content-Type", equalTo("application/task+json")));

        verify(postRequestedFor(urlEqualTo("/auction-house/internal/create-auction-for-task/"))
                .withHeader("Content-Type", equalTo("application/task+json")));
    }

}
