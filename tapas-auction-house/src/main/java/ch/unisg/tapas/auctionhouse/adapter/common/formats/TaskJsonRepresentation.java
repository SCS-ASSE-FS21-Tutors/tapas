package ch.unisg.tapas.auctionhouse.adapter.common.formats;

import ch.unisg.tapas.auctionhouse.domain.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;


public class TaskJsonRepresentation {
    public static final String MEDIA_TYPE = "application/task+json";

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
        this.originalTaskUri = ((task.getOriginalTaskUri() != null) ? task.getOriginalTaskUri().getValue() : null);
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
