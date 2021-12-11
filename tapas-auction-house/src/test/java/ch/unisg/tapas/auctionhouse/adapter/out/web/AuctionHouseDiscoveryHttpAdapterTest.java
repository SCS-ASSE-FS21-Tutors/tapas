package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.client.WireMock;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuctionHouseDiscoveryHttpAdapterTest {

    private static WireMockServer mockServer;

    @BeforeAll
    public static void setUp(){

        mockServer = new WireMockServer();
        mockServer.start();
    }

    @AfterAll
    public static void tearDown(){

        mockServer.stop();
    }


    @Test
    public void testLoadAuctionHouseDiscovery() throws Exception {

        // ARRANGE
        var sampleResponse = "{\n" +
            "  \"auctionHouseInfo\": [\n" +
            "    {\n" +
            "      \"auctionhouseuri\":\"http://example.org\",\n" +
            "      \"websuburi\":\"http://example.org\",\n" +
            "      \"taskTypes\":[\"COMPUTATION\", \"RANDOMTEXT\"],\n" +
            "      \"timeStamp\":\"2021-12-24 12:00:00\",\n" +
            "      \"groupName\":\"Group3\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"auctionhouseuri\":\"http://facemash.com\",\n" +
            "      \"websuburi\":\"http://facemash.com\",\n" +
            "      \"taskTypes\":[\"BIGROBOT\"],\n" +
            "      \"timeStamp\":\"2021-12-24 12:00:00\",\n" +
            "      \"groupName\":\"Group2\"\n" +
            "    }\n" +
            "  ]\n" +
            "  }";

        mockServer.stubFor(WireMock.get("/discovery/")
            .willReturn(WireMock.aResponse()
                .withBody(sampleResponse)
                .withHeader("Content-Type", "application/auctionhousediscovery+json")
                .withStatus(200)));

        var mockUri = URI.create("http://localhost:" + mockServer.port() + "");

        // ACT
        var port = new AuctionHouseDiscoveryHttpAdapter();
        List<AuctionHouseInformation> info = port.loadDiscoveryInfo(mockUri);

        // Assert
        assertEquals(2, info.size());

        var i= info.get(0);
        assertEquals("http://example.org", i.getAuctionhouseuri().toString());
        assertEquals("http://example.org", i.getWebsuburi().toString());
        assertEquals("[Task.TaskType(value=COMPUTATION), Task.TaskType(value=RANDOMTEXT)]", i.getTaskTypes().toString());
        assertEquals("2021-12-24 12:00:00", i.getTimeStamp().getValue().toString());
        assertEquals("Group3", i.getGroupName().getValue());
    }

}
