package ch.unisg.tapas.roster.application.services;

import ch.unisg.tapas.roster.application.port.in.RostNewTaskUseCase;
import ch.unisg.tapas.roster.application.port.out.AuctionHousePort;
import ch.unisg.tapas.roster.application.port.out.ExecutorPoolPort;
import ch.unisg.tapas.roster.entities.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicRosterService implements RostNewTaskUseCase {

    private final AuctionHousePort auctionHousePort;
    private final ExecutorPoolPort executorPoolPort;

    @Override
    public void rostTask(Task newTask){

        boolean canExecuteInternally = executorPoolPort.canExecuteInternally(newTask);

        if(canExecuteInternally)
            executorPoolPort.executeInternally(newTask);
        else
            auctionHousePort.executeExternally(newTask);
    }
}
