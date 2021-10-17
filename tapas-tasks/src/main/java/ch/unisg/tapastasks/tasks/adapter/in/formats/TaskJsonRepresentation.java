package ch.unisg.tapastasks.tasks.adapter.in.formats;

import ch.unisg.tapastasks.tasks.domain.Task;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO Andrei: add comments
 */
final public class TaskJsonRepresentation {
    // The media type used for this task representation format. The media type is just an identifier
    // and can be registered with IANA to support interoperability.
    public static final String TASK_MEDIA_TYPE = "application/task+json";

    @Getter @Setter
    private String taskId;
    @Getter
    private final String taskName;
    @Getter
    private final String taskType;
    @Getter @Setter
    private String taskStatus;

    @Getter @Setter
    private String originalTaskUri;
    @Getter @Setter
    private String serviceProvider;

    @Getter @Setter
    private String inputData;
    @Getter @Setter
    private String outputData;

    public TaskJsonRepresentation(String taskName, String taskType) {
        this.taskName = taskName;
        this.taskType = taskType;

        this.taskStatus = null;
        this.originalTaskUri = null;
        this.serviceProvider = null;
        this.inputData = null;
        this.outputData = null;
    }

    public TaskJsonRepresentation(Task task) {
        this(task.getTaskName().getValue(), task.getTaskType().getValue());

        this.taskId = task.getTaskId().getValue();
        this.taskStatus = task.getTaskStatus().getValue().name();

        this.originalTaskUri = (task.getOriginalTaskUri() == null) ?
            null : task.getOriginalTaskUri().getValue();

        this.serviceProvider = (task.getProvider() == null) ? null : task.getProvider().getValue();
        this.inputData = (task.getInputData() == null) ? null : task.getInputData().getValue();
        this.outputData = (task.getOutputData() == null) ? null : task.getOutputData().getValue();
    }
    
    public static String serialize(Task task) throws JsonProcessingException {
        TaskJsonRepresentation representation = new TaskJsonRepresentation(task);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(representation);
    }
}
