package ch.unisg.tapas.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class HypermediaDiscoveryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDiscoveryEndpoint() throws Exception {

        // ACT
        mockMvc.perform(MockMvcRequestBuilders.get("/discovery/")
            .header("Content-Type", "application/auctionhousediscovery+json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.auctionHouseInfo[0].auctionHouseUri").isString())
            .andExpect(jsonPath("$.auctionHouseInfo[0].webSubUri").isString())
            .andExpect(jsonPath("$.auctionHouseInfo[0].taskTypes").isArray())
            .andExpect(jsonPath("$.auctionHouseInfo[0].timeStamp").isString())
            .andExpect(jsonPath("$.auctionHouseInfo[0].groupName").isString());
    }
}
