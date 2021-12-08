package ch.unisg.tapas.auctionhouse.domain;

import ch.unisg.tapas.auctionhouse.application.port.out.AuctionHouseDiscoveryPort;
import ch.unisg.tapas.auctionhouse.application.service.AuctionHouseDiscoveryService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuctionHouseDiscoveryServiceTest {

    // ARRANGE set up a mock graph of auction houses
    final List<Task.TaskType> taskTypes = Lists.emptyList();
    final AuctionHouseInformation.AuctionHouseTimeStamp timestamp = new AuctionHouseInformation.AuctionHouseTimeStamp("12:93");

    @Test
    public void testAuctionHouseDiscovery() throws Exception {

        // Arrange simulate 3 discovery endpoints
        var uri1 = new URI("http://localhost:9999/1");
        var info1 = new AuctionHouseInformation(uri1, uri1, taskTypes, timestamp, new AuctionHouseInformation.GroupName("1"));
        var uri2 = new URI("http://localhost:9999/2");
        var info2 = new AuctionHouseInformation(uri2, uri2, taskTypes, timestamp, new AuctionHouseInformation.GroupName("2"));
        var uri3 = new URI("http://localhost:9999/3");
        var info3 = new AuctionHouseInformation(uri3, uri3, taskTypes, timestamp, new AuctionHouseInformation.GroupName("3"));
        var uri4 = new URI("http://localhost:9999/4");
        var info4 = new AuctionHouseInformation(uri4, uri4, taskTypes, timestamp, new AuctionHouseInformation.GroupName("4"));

        var discoveryPort = Mockito.mock(AuctionHouseDiscoveryPort.class);

        // 1 knows 1 and 2
        Mockito.when(discoveryPort.loadDiscoveryInfo(uri1)).thenReturn(Lists.list(info1, info2));
        // 2 knows 3
        Mockito.when(discoveryPort.loadDiscoveryInfo(uri2)).thenReturn(Lists.list(info3));
        // 3 knows 1, 3 and 4
        Mockito.when(discoveryPort.loadDiscoveryInfo(uri3)).thenReturn(Lists.list(info1, info3, info4));
        // 4 throws error
        Mockito.when(discoveryPort.loadDiscoveryInfo(uri4)).thenThrow(RuntimeException.class);


        // ACT
        var service = new AuctionHouseDiscoveryService(discoveryPort);
        Collection<AuctionHouseInformation> auctionHouseInformation = service.discoverAuctionHouses(uri1);

        // ASSERT
        assertTrue(auctionHouseInformation.contains(info1));
        assertTrue(auctionHouseInformation.contains(info2));
        assertTrue(auctionHouseInformation.contains(info3));
        assertTrue(auctionHouseInformation.contains(info4));
        assertEquals(4, auctionHouseInformation.size());
    }
}
