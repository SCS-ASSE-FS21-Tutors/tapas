package ch.unisg.tapasexecutordigital.executordigital.application.service;

import ch.unisg.tapasexecutordigital.executordigital.application.port.in.AssignTaskUseCase;
import ch.unisg.tapasexecutordigital.executordigital.application.port.in.AssignTaskCommand;
import ch.unisg.tapasexecutordigital.executordigital.domain.Executor;
import ch.unisg.tapasexecutordigital.executordigital.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class AssignTaskToExecutorService implements AssignTaskUseCase{

    @Override
    public void assignTask(AssignTaskCommand command) {
        Executor executor = Executor.getExecutor();
        if(executor.getAssignedTask() == null) {
            Task task = new Task(command.getTaskName(), command.getTaskType());
            executor.assignTask(task);
            System.out.println("Task assigned to executor");
        }
    }

}
