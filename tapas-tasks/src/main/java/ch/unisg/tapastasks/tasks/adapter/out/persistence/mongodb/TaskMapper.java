package ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb;

import ch.unisg.tapastasks.tasks.domain.Task;
import org.springframework.stereotype.Component;

@Component
class TaskMapper {

    Task mapToDomainEntity(MongoTaskDocument task) {
        return new Task(
            new Task.TaskId(task.taskId),
            new Task.TaskName(task.taskName),
            new Task.TaskType(task.taskType),
            new Task.OriginalTaskUri(task.originalTaskUri),
            new Task.TaskStatus(Task.Status.valueOf(task.taskStatus)),
            new Task.ServiceProvider(task.provider),
            new Task.InputData(task.inputData),
            new Task.OutputData(task.outputData)
        );
    }

    MongoTaskDocument mapToMongoDocument(Task task) {
        return new MongoTaskDocument(
            task.getTaskId().getValue(),
            task.getTaskName().getValue(),
            task.getTaskType().getValue(),
            task.getOriginalTaskUri().getValue(),
            task.getTaskStatus().getValue().toString(),
            task.getProvider().getValue(),
            task.getInputData().getValue(),
            task.getOutputData().getValue()
        );
    }
}
