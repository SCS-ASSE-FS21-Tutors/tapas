package ch.unisg.roster.roster.application.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import ch.unisg.roster.roster.application.port.in.ApplyForTaskCommand;
import ch.unisg.roster.roster.application.port.in.ApplyForTaskUseCase;
import ch.unisg.roster.roster.application.port.out.TaskAssignedEventPort;
import ch.unisg.roster.roster.domain.Roster;
import ch.unisg.roster.roster.domain.Task;
import ch.unisg.roster.roster.domain.event.TaskAssignedEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Transactional
public class ApplyForTaskService implements ApplyForTaskUseCase {

    private final TaskAssignedEventPort taskAssignedEventPort;

    /**
    *   Checks if a task is available and assignes it to the executor. If task got assigned a task
    *   assigned event gets published.
    *   @return assigned task or null if no task is found
    **/
    @Override
    public Task applyForTask(ApplyForTaskCommand command) {
        Task task = Roster.getInstance().assignTaskToExecutor(command.getTaskType(),
            command.getExecutorURI());

        if (task != null) {
            taskAssignedEventPort.publishTaskAssignedEvent(new TaskAssignedEvent(task.getTaskID()));
        }

        return task;
    }

}
