package ch.unisg.tapas.auctionhouse.adapter.common.formats.dto;

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
