package ch.unisg.tapas.auctionhouse.adapter.common.formats;

import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;
import ch.unisg.tapas.auctionhouse.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionHouseDiscoveryRepresentation {

    private List<AuctionHouseInformationDto> auctionHouseInfo;

    @Data
    @NoArgsConstructor
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
                .map(type -> type.getValue())
                .collect(Collectors.toList());
        }

        public AuctionHouseInformation toDomainObject(){

            var taskTypes = this.taskTypes.stream().map(Task.TaskType::new).collect(Collectors.toList());

            return new AuctionHouseInformation(
                URI.create(auctionhouseuri),
                URI.create(websuburi),
                taskTypes,
                new AuctionHouseInformation.AuctionHouseTimeStamp(timeStamp),
                new AuctionHouseInformation.GroupName(groupName));
        }

    }

    public AuctionHouseDiscoveryRepresentation(Collection<AuctionHouseInformation> auctionHouseInformationCollection) {

        this.auctionHouseInfo = auctionHouseInformationCollection.stream()
            .map(a -> new AuctionHouseInformationDto(a))
            .collect(Collectors.toList());
    }

    public List<AuctionHouseInformation> toDomainObject(){

        return this.auctionHouseInfo.stream()
            .map(AuctionHouseInformationDto::toDomainObject)
            .collect(Collectors.toList());
    }
}
