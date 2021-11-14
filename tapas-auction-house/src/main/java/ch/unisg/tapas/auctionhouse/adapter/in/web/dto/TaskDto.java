package ch.unisg.tapas.auctionhouse.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDto {

    private String taskId;
    private String taskName;
    private String taskType;
    private String taskStatus;
    private String originalTaskUri;
    private String serviceProvider;
    private String inputData;
    private String outputData;
}
