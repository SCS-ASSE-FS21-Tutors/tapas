package ch.unisg.tapas.auctionhouse.application.service;

import ch.unisg.tapas.auctionhouse.application.port.in.ProcessExternalTaskCommand;
import ch.unisg.tapas.auctionhouse.application.port.in.ProcessExternalTaskUseCase;
import ch.unisg.tapas.auctionhouse.application.port.out.*;
import ch.unisg.tapas.auctionhouse.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessExternalTaskService implements ProcessExternalTaskUseCase {

    @Autowired
    ExecuteExternalTaskCommandPort executeExternalTaskCommandPort;

    @Autowired
    UpdateExternalTaskCommandPort updateExternalTaskCommandPort;

    @Override
    public boolean processExternalTask(ProcessExternalTaskCommand command){
        ExecuteExternalTaskCommand executeCommand = new ExecuteExternalTaskCommand(command.getTask());
        boolean sent = executeExternalTaskCommandPort.executeExternalTask(executeCommand);
        if(sent){
            UpdateExternalTaskCommand updateCommand = new UpdateExternalTaskCommand(command.getTask(), new Task.TaskStatus(Task.Status.ASSIGNED));
            boolean updated = updateExternalTaskCommandPort.updateExternalTask(updateCommand);
            return updated;
        }else return false;
    }
}
