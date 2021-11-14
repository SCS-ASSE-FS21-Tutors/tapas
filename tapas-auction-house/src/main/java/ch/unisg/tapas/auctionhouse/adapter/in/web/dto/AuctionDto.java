package ch.unisg.tapas.auctionhouse.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuctionDto {

    private String auctionId;
    private String auctionHouseUri;
    private String taskUri;
    private String taskType;
    private String deadline;
}
