package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapasroster.roster.application.port.in.ScheduleTaskCommand;
import ch.unisg.tapasroster.roster.application.port.in.ScheduleTaskUseCase;
import ch.unisg.tapasroster.roster.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class ScheduleTaskService implements ScheduleTaskUseCase {
    @Override
    public Task scheduleTask(ScheduleTaskCommand command) {
        System.out.println("New Task scheduled");
        System.out.println(command.getTaskName());
        System.out.println(command.getTaskId());
        System.out.println(command.getTaskType());
        return new Task();
    }
}
