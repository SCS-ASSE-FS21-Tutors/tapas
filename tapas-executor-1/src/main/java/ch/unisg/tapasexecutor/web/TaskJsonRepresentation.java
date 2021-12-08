package ch.unisg.tapasexecutor.web;

import ch.unisg.tapasexecutor.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskJsonRepresentation {
    public static final String MEDIA_TYPE = "application/task+json";

    private String taskId;
    private String taskName;
    private String taskType;
    private String taskStatus;
    private String originalTaskUri;
    private String serviceProvider;
    private String inputData;
    private String outputData;

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

    public Task toTask() {
        Task task = new Task(
                new Task.TaskId(this.getTaskId()),
                new Task.TaskName(this.getTaskName()),
                new Task.TaskType(this.getTaskType()),
                new Task.OriginalTaskUri(this.getOriginalTaskUri()),
                new Task.TaskStatus(Task.Status.valueOf("string".equals(taskStatus) ? "OPEN" : taskStatus)),
                new Task.ServiceProvider(this.getServiceProvider()),
                new Task.InputData(this.getInputData()),
                new Task.OutputData(this.getOutputData())
        );
        return task;
    }
}
