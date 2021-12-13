package ch.unisg.tapastasks.tasks.adapter.in.formats;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Since we do not want the user to send an entire task object to the controller,
 * we only request a few inputs that are needed from the user.
 */
@AllArgsConstructor
final public class NewTaskJsonRepresentation {
    public static final String MEDIA_TYPE = "application/json";

    @Getter @Schema(example = "example-task-name")
    private final String taskName;

    @Getter @Schema(example = "COMPUTATION")
    private final String taskType;

    @Getter @Setter @Schema(example = "1+2+4")
    private String inputData;


}

