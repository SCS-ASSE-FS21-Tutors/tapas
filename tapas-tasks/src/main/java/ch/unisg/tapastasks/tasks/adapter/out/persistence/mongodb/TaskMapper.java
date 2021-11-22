package ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb;

import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import org.springframework.stereotype.Component;

@Component
class TaskMapper {
    Task mapToDomainEntity(MongoTaskDocument task) {
        return new Task(
            new Task.TaskId(task.taskId),
            new Task.TaskName(task.taskName),
            new Task.TaskType(Task.Type.valueOf(task.taskType)),
            new Task.OriginalTaskUri(task.originalTaskUri),
            new Task.TaskStatus(Task.Status.valueOf(task.taskStatus)),
            new Task.ServiceProvider(task.getProvider()),
            new Task.InputData(task.getInputData()),
            new Task.OutputData(task.getOutputData())
        );
    }

    MongoTaskDocument mapToMongoDocument(Task task) {
        return new MongoTaskDocument(
            task.getTaskId().getValue(),
            task.getTaskName().getValue(),
            task.getTaskType().getValue().name(),
            task.getOriginalTaskUri().getValue(),
            task.getTaskStatus().getValue().toString(),
            task.getProvider().getValue(),
            task.getInputData().getValue(),
            task.getOutputData().getValue(),
            TaskList.getTapasTaskList().getTaskListName().getValue()
        );
    }
}
