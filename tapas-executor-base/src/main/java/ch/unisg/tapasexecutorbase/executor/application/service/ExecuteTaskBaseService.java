package ch.unisg.tapasexecutorbase.executor.application.service;

import ch.unisg.tapascommon.ServiceHostAddresses;
import ch.unisg.tapascommon.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapasexecutorbase.executor.application.port.in.ExecuteTaskCommand;
import ch.unisg.tapasexecutorbase.executor.application.port.in.ExecuteTaskUseCase;
import ch.unisg.tapasexecutorbase.executor.application.port.out.TaskUpdatedEventPort;
import ch.unisg.tapasexecutorbase.executor.domain.TaskUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExecuteTaskBaseService implements ExecuteTaskUseCase {

    public static void updateTaskStatus(Task task, TaskUpdatedEventPort taskUpdatedEventPort) {
        var taskRepresentation = new TaskJsonRepresentation(task);
        var ownTaskUri = ServiceHostAddresses.getTaskServiceHostAddress() + "/tasks/" + taskRepresentation.getTaskId();
        var updateTaskEvent = new TaskUpdatedEvent(
                taskRepresentation.getTaskId(),
                taskRepresentation.getTaskStatus(),
                taskRepresentation.getOutputData(),
                ownTaskUri);
        taskUpdatedEventPort.updateTaskStatusEvent(updateTaskEvent);

        if (!taskRepresentation.getOriginalTaskUri().isEmpty()) {
            var updateDelegatedTaskEvent = new TaskUpdatedEvent(
                    taskRepresentation.getTaskId(),
                    taskRepresentation.getTaskStatus(),
                    taskRepresentation.getOutputData(),
                    taskRepresentation.getOriginalTaskUri());
            taskUpdatedEventPort.updateTaskStatusEvent(updateDelegatedTaskEvent);
        }
    }

    @Override
    public void executeTask(ExecuteTaskCommand command) { }
}
