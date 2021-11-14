package ch.unisg.tapas.auctionhouse.adapter.common.formats.dto;

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
