package ch.unisg.assignment.assignment.application.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import ch.unisg.assignment.assignment.application.port.in.ApplyForTaskCommand;
import ch.unisg.assignment.assignment.application.port.in.ApplyForTaskUseCase;
import ch.unisg.assignment.assignment.application.port.out.TaskAssignedEventPort;
import ch.unisg.assignment.assignment.domain.Roster;
import ch.unisg.assignment.assignment.domain.Task;
import ch.unisg.assignment.assignment.domain.event.TaskAssignedEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Transactional
public class ApplyForTaskService implements ApplyForTaskUseCase {

    private final TaskAssignedEventPort taskAssignedEventPort;

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
