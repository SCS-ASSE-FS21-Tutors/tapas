package ch.unisg.tapas.auctionhouse.domain;

import lombok.Value;

import java.net.URI;
import java.util.List;

@Value
public class AuctionHouseInformation {

    URI auctionhouseuri;
    URI websuburi;
    List<Task.TaskType> taskTypes;
    AuctionHouseTimeStamp timeStamp;
    GroupName groupName;

    @Value
    public static class AuctionHouseTimeStamp{
        String value;
    }

    @Value
    public static class GroupName{
        String value;
    }
}
