package ch.unisg.tapas.tapasexecutor2.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskJsonRepresentation {
    public static final String MEDIA_TYPE = "application/task+json";

    @Schema(example = "45501578-fef7-45d7-9adc-c182e79b0820")
    private String taskId;

    @Schema(example = "Robot Dance Command")
    private String taskName;

    @Schema(example = "COMPUTATION")
    private String taskType;

    @Schema(example = "ASSIGNED")
    private String taskStatus;

    @Schema(example = "https://tapas-tasks.86-119-34-242.nip.io/task/123")
    private String originalTaskUri;

    @Schema(example = "")
    private String serviceProvider;

    @Schema(example = "(1+7)*5")
    private String inputData;

    @Schema(example = "")
    private String outputData;
}
