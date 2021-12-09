package ch.unisg.tapas.tapasexecutor2.application;

import ch.unisg.tapas.tapasexecutor2.web.TaskJsonRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ExecutorService {

    @Autowired
    ComputationService computationService;

    @Autowired
    UpdateTaskHttpAdapter updateTaskHttpAdapter;

    @Async
    public void executeTask(TaskJsonRepresentation task) {

        var output = computationService.executeComputation(task.getInputData());

        task.setTaskStatus("EXECUTED");
        task.setOutputData(output);

        updateTaskHttpAdapter.setTaskComplete(task);
    }
}
