package ch.unisg.tapas.auctionhouse.application.service;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.WebSubConfig;
import ch.unisg.tapas.auctionhouse.application.port.in.ProvideDiscoveredAuctionHousesCommand;
import ch.unisg.tapas.auctionhouse.application.port.in.ProvideDiscoveredAuctionHousesUseCase;
import ch.unisg.tapas.auctionhouse.domain.DiscoveredAuctionHouseInfo;
import ch.unisg.tapas.auctionhouse.domain.DiscoveredAuctionHouseRegistry;
import ch.unisg.tapas.common.ConfigProperties;
import ch.unisg.tapascommon.communication.ServiceHostAddresses;
import ch.unisg.tapascommon.tasks.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public class ProvideDiscoveredAuctionHousesService implements ProvideDiscoveredAuctionHousesUseCase {

    private final WebSubConfig webSubConfig;

    @Override
    public DiscoveredAuctionHouseInfo[] provideDiscoveredAuctionHouses(ProvideDiscoveredAuctionHousesCommand command) {
        var infos = DiscoveredAuctionHouseRegistry.getInstance()
            .getDiscoveredAuctionHouses();

        var ownInfo = new DiscoveredAuctionHouseInfo();
        ownInfo.setAuctionHouseUri(ServiceHostAddresses.getAuctionHouseServiceHostAddress());
        ownInfo.setTaskTypes(new String[]{ Task.Type.COMPUTATION.name(), Task.Type.BIGROBOT.name() });
        ownInfo.setGroupName(webSubConfig.getGroup());
        ownInfo.setWebSubUri(ServiceHostAddresses.getAuctionHouseServiceHostAddress());
        ownInfo.setTimestamp(Timestamp.from(ZonedDateTime.now().toInstant()));

        var infosInclusiveOwn = new ArrayList<DiscoveredAuctionHouseInfo>();
        infosInclusiveOwn.add(ownInfo);
        infosInclusiveOwn.addAll(infos);

        return infosInclusiveOwn.toArray(new DiscoveredAuctionHouseInfo[0]);
    }
}
