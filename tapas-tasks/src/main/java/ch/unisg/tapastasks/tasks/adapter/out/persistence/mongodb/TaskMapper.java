package ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb;

import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import org.springframework.stereotype.Component;

@Component
class TaskMapper {

    Task mapToDomainEntity(MongoTaskDocument task) {
        return Task.withIdNameTypeOriginaluriStatus(
            new Task.TaskId(task.taskId),
            new Task.TaskName(task.taskName),
            new Task.TaskType(task.taskType),
            new Task.OriginalTaskUri(task.originalTaskUri),
            new Task.TaskStatus(Task.Status.valueOf(task.taskStatus))
        );
    }

    MongoTaskDocument mapToMongoDocument(Task task) {
        return new MongoTaskDocument(
            task.getTaskId().getValue(),
            task.getTaskName().getValue(),
            task.getTaskType().getValue(),
            task.getOriginalTaskUri().getValue(),
            task.getTaskStatus().getValue().toString(),
            TaskList.getTapasTaskList().getTaskListName().getValue()
        );
    }
}
