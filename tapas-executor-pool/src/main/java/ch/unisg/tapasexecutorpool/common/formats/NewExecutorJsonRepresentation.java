package ch.unisg.tapasexecutorpool.common.formats;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
final public class NewExecutorJsonRepresentation {
    public static final String MEDIA_TYPE = "application/json";

    @Getter @Schema(example = "example-executor-name")
    private final String executorName;

    @Getter @Schema(example = "COMPUTATION")
    private final String executorType;

    @Getter @Schema(example = "https://tapas-executor-2.86-119-34-242.nip.io/")
    private final String executorUrl;


}

