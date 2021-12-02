package ch.unisg.tapas.auctionhouse.domain;

import lombok.Value;

import java.net.URI;
import java.util.List;

@Value
public class AuctionHouseInformation {

    private URI auctionhouseuri;
    private URI websuburi;
    private List<Task.TaskType> taskTypes;
    private AuctionHouseTimeStamp timeStamp;
    private GroupName groupName;

    @Value
    public static class AuctionHouseTimeStamp{
        private String value;
    }

    @Value
    public static class GroupName{
        private String value;
    }
}
