package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapasroster.roster.application.port.in.ScheduleTaskCommand;
import ch.unisg.tapasroster.roster.application.port.in.ScheduleTaskUseCase;
import ch.unisg.tapasroster.roster.application.port.out.ExecuteTaskOnPoolEventPort;
import ch.unisg.tapasroster.roster.domain.ForwardTaskToPoolEvent;
import ch.unisg.tapasroster.roster.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class ScheduleTaskService implements ScheduleTaskUseCase {

    private final ExecuteTaskOnPoolEventPort executeTaskOnPoolEventPort;

    @Override
    public Task scheduleTask(ScheduleTaskCommand command) {
        var task = command.getTask();
        System.out.println("New Task scheduled");
        System.out.println(task.getTaskName().getValue());
        System.out.println(task.getTaskId().getValue());
        System.out.println(task.getTaskType().getValue());

        var event = new ForwardTaskToPoolEvent(task);
        executeTaskOnPoolEventPort.forwardTaskToPoolEvent(event);
        return task;
    }
}
