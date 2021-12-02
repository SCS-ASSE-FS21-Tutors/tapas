package ch.unisg.tapas.auctionhouse.adapter.common.formats;

import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;
import ch.unisg.tapas.auctionhouse.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class AuctionHouseDiscoveryRepresentation {

    private List<AuctionHouseInformationDto> auctionHouseInfo;

    @Data
    @AllArgsConstructor
    public static class AuctionHouseInformationDto{

        private String auctionhouseuri;
        private String websuburi;
        private List<String> taskTypes;
        private String timeStamp;
        private String groupName;

        public AuctionHouseInformationDto(AuctionHouseInformation auctionHouseInformation) {

            this.auctionhouseuri = auctionHouseInformation.getAuctionhouseuri().toString();
            this.websuburi = auctionHouseInformation.getWebsuburi().toString();
            this.timeStamp = auctionHouseInformation.getTimeStamp().getValue();
            this.groupName = auctionHouseInformation.getGroupName().getValue();

            this.taskTypes = auctionHouseInformation.getTaskTypes().stream()
                .map(Task.TaskType::toString)
                .collect(Collectors.toList());
        }
    }

    public AuctionHouseDiscoveryRepresentation(Collection<AuctionHouseInformation> auctionHouseInformationCollection) {

        this.auctionHouseInfo = auctionHouseInformationCollection.stream()
            .map(a -> new AuctionHouseInformationDto(a))
            .collect(Collectors.toList());
    }
}
