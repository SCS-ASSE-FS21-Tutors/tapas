package ch.unisg.tapas.auctionhouse.application.service;

import ch.unisg.tapas.auctionhouse.application.port.in.TaskWonCommand;
import ch.unisg.tapas.auctionhouse.application.port.in.TaskWonUseCase;
import ch.unisg.tapas.auctionhouse.application.port.out.TaskWonEventPort;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;

@Component
@Transactional
public class TaskWonService implements TaskWonUseCase {
    private final TaskWonEventPort taskWonEventPort;

    public TaskWonService(TaskWonEventPort taskWonEventPort) {
        this.taskWonEventPort = taskWonEventPort;
    }

    @Override
    public boolean addWonTaskToTaskList(TaskWonCommand command) {
        boolean success  = taskWonEventPort.addWonTaskToTaskList(command.getTaskJson());
        return  success;

    }
}
