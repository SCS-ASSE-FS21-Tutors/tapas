package ch.unisg.tapas.auctionhouse.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BidDto {

    private String auctionId;
    private String bidderName;
    private String bidderAuctionHouseUri;
    private String bidderTaskListUri;
}
