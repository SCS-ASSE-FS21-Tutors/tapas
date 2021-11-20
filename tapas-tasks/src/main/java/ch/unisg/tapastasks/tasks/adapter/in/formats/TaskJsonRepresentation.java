package ch.unisg.tapastasks.tasks.adapter.in.formats;

import ch.unisg.tapastasks.tasks.domain.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to expose and consume representations of tasks through the HTTP interface. The
 * representations conform to the custom JSON-based media type "application/task+json". The media type
 * is just an identifier and can be registered with
 * <a href="https://www.iana.org/assignments/media-types/">IANA</a> to promote interoperability.
 */
final public class TaskJsonRepresentation {
    // The media type used for this task representation format
    public static final String MEDIA_TYPE = "application/task+json";

    // A task identifier specific to our implementation (e.g., a UUID). This identifier is then used
    // to generate the task's URI. URIs are standard uniform identifiers and use a universal syntax
    // that can be referenced (and dereferenced) independent of context. In our uniform HTTP API,
    // we identify tasks via URIs and not implementation-specific identifiers.
    @Getter @Setter
    private String taskId;

    // A string that represents the task's name
    @Getter
    private final String taskName;

    // A string that identifies the task's type. This string could also be a URI (e.g., defined in some
    // Web ontology, as we shall see later in the course), but it's not constrained to be a URI.
    // The task's type can be used to assign executors to tasks, to decide what tasks to bid for, etc.
    @Getter
    private final String taskType;

    // The task's status: OPEN, ASSIGNED, RUNNING, or EXECUTED (see Task.Status)
    @Getter @Setter
    private String taskStatus;

    // If this task is a delegated task (i.e., a shadow of another task), this URI points to the
    // original task. Because URIs are standard and uniform, we can just dereference this URI to
    // retrieve a representation of the original task.
    @Getter @Setter
    private String originalTaskUri;

    // The service provider who executes this task. The service provider is a any string that identifies
    // a TAPAS group (e.g., tapas-group1). This identifier could also be a URI (if we have a good reason
    // for it), but it's not constrained to be a URI.
    @Getter @Setter
    private String serviceProvider;

    // A string that provides domain-specific input data for this task. In the context of this project,
    // we can parse and interpret the input data based on the task's type.
    @Getter @Setter
    private String inputData;

    // A string that provides domain-specific output data for this task. In the context of this project,
    // we can parse and interpret the output data based on the task's type.
    @Getter @Setter
    private String outputData;


    public TaskJsonRepresentation(String taskId, String taskName, String taskType, String taskStatus, String originalTaskUri, String serviceProvider, String inputData, String outputData) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskType = taskType;
        this.taskStatus = taskStatus;
        this.originalTaskUri = originalTaskUri;
        this.serviceProvider = serviceProvider;
        this.inputData = inputData;
        this.outputData = outputData;
    }

    public TaskJsonRepresentation(Task task){
        this.taskId = task.getTaskId().getValue();
        this.taskName = task.getTaskName().getValue();
        this.taskType = task.getTaskType().getValue();
        this.taskStatus = task.getTaskStatus().getValue().toString();
        this.originalTaskUri = task.getOriginalTaskUri().getValue();
        this.serviceProvider = ((task.getProvider() != null) ? task.getProvider().getValue() : null);
        this.inputData = ((task.getInputData() != null) ? task.getInputData().getValue() : null);
        this.outputData = ((task.getOutputData() != null) ? task.getOutputData().getValue() : null);
    }


    public static String serialize(Task task) throws JsonProcessingException {
        TaskJsonRepresentation representation = new TaskJsonRepresentation(task);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(representation);
    }
    public static Task deserialize(String taskJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(taskJson);
        Task.TaskId taskId = new Task.TaskId(jsonNode.get("taskId").asText());
        Task.TaskName taskName = new Task.TaskName((jsonNode.get("taskName").asText()));
        Task.TaskType taskType = new Task.TaskType(jsonNode.get("taskType").asText());
        Task.OriginalTaskUri originalTaskUri= new Task.OriginalTaskUri(jsonNode.get("originalTaskUri").asText());
        Task.TaskStatus taskStatus = new Task.TaskStatus(Task.Status.valueOf(jsonNode.get("taskStatus").asText()));
        Task.ServiceProvider serviceProvider = new Task.ServiceProvider(jsonNode.get("serviceProvider").asText());
        Task.InputData inputData = new Task.InputData(jsonNode.get("inputData").asText());
        Task.OutputData outputData = new Task.OutputData(jsonNode.get("outputData").asText());

        Task task = new Task(taskId,taskName, taskType, originalTaskUri, taskStatus, serviceProvider, inputData, outputData);
        return task;
    }
    public static Task toTask(TaskJsonRepresentation taskJsonRepresentation) {
        Task task = new Task(
            new Task.TaskId(taskJsonRepresentation.getTaskId()),
            new Task.TaskName(taskJsonRepresentation.getTaskName()),
            new Task.TaskType(taskJsonRepresentation.getTaskType()),
            new Task.OriginalTaskUri(taskJsonRepresentation.getOriginalTaskUri()),
            new Task.TaskStatus(Task.Status.valueOf(taskJsonRepresentation.getTaskStatus())),
            new Task.ServiceProvider(taskJsonRepresentation.getServiceProvider()),
            new Task.InputData(taskJsonRepresentation.getInputData()),
            new Task.OutputData(taskJsonRepresentation.getOutputData())
        );
        return task;
    }


}
