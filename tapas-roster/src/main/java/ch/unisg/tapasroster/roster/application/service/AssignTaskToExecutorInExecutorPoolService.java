package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorInExecutorPoolCommand;
import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorInExecutorPoolUseCase;
import ch.unisg.tapasroster.roster.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class AssignTaskToExecutorInExecutorPoolService implements AssignTaskToExecutorInExecutorPoolUseCase {


    @Override
    public Task assignTaskToExecutor(AssignTaskToExecutorInExecutorPoolCommand command) {
        System.out.println("It works");
        //IN:Task from TaskList
        // get executors via port
        // find matching type (task==executor)
        // send request to executor for execution
        //OUT:Later will be current status of the TaskList
        return null;
    }
}