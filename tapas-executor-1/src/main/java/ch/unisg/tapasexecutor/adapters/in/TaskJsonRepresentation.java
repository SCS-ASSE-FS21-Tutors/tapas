package ch.unisg.tapasexecutor.adapters.in;

import ch.unisg.tapasexecutor.domain.Task;
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

    @Schema(example = "SMALLROBOT")
    private String taskType;

    @Schema(example = "ASSIGNED")
    private String taskStatus;

    @Schema(example = "https://tapas-tasks.86-119-34-242.nip.io/task/123")
    private String originalTaskUri;

    @Schema(example = "")
    private String serviceProvider;

    @Schema(example = "")
    private String inputData;

    @Schema(example = "")
    private String outputData;

    public TaskJsonRepresentation(Task task) {
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
