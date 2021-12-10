package ch.unisg.tapas.auctionhouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DiscoveredAuctionHouseInfo {

    private String auctionHouseUri;
    private String webSubUri;
    private String[] taskTypes;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp timeStamp;

    private String groupName;

    @Override
    public String toString() {
        return "DiscoveredAuctionHouseInfo{" +
            "auctionHouseUri='" + auctionHouseUri + '\'' +
            ", webSubUri='" + webSubUri + '\'' +
            ", taskTypes=" + Arrays.toString(taskTypes) +
            ", timeStamp=" + timeStamp +
            ", groupName='" + groupName + '\'' +
            '}';
    }
}
