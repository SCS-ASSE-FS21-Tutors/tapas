package ch.unisg.tapasexecutorpool.common.formats;

import ch.unisg.tapasexecutorpool.pool.domain.Task;
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
        this.originalTaskUri = task.getOriginalTaskUri().getValue();
        this.serviceProvider = task.getProvider().getValue();
        this.inputData = task.getInputData().getValue();
        this.outputData = task.getOutputData().toString();
    }

    public static String serialize(Task task) throws JsonProcessingException {
        TaskJsonRepresentation representation = new TaskJsonRepresentation(task);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(representation);
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
        new Task.OutputData(taskJsonRepresentation.getOutputData()),
                false
        );
        return task;
    }


}