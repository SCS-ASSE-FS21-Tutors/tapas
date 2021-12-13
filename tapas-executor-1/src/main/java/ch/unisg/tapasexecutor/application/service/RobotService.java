package ch.unisg.tapasexecutor.application.service;

import ch.unisg.tapasexecutor.application.ports.in.ExecuteRobotTaskUseCase;
import ch.unisg.tapasexecutor.application.ports.in.IsTaskAcceptableQuery;
import ch.unisg.tapasexecutor.application.ports.out.QuerySearchEnginePort;
import ch.unisg.tapasexecutor.application.ports.out.RobotPort;
import ch.unisg.tapasexecutor.application.ports.out.UpdateTaskPort;
import ch.unisg.tapasexecutor.domain.Task;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
public class RobotService implements IsTaskAcceptableQuery, ExecuteRobotTaskUseCase {

    @Autowired
    private UpdateTaskPort updateTaskPort;

    @Autowired
    private RobotPort robotPort;

    @Autowired
    private QuerySearchEnginePort querySearchEnginePort;

    @Override
    @Async("threadPoolTaskExecutor")
    public void executeRobotTask(Task task) {

        if (!isAcceptable(task))
            throw new IllegalArgumentException("Task is not acceptable");

        // Query Search engine for things description of robot
        Optional<String> robotTDUriOptional = querySearchEnginePort.querySearchEngine();
        if (robotTDUriOptional.isEmpty())
            throw new RuntimeException("No TD uri could be returned from search engine");
        String robotTDUri = robotTDUriOptional.get();

        var outputData = robotPort.executeTask(task.getInputData().getValue(), robotTDUri);
        task.setOutputData(new Task.OutputData(outputData));

        updateTaskPort.setTaskComplete(task);
    }

    @Override
    public boolean isAcceptable(Task task) {

        return task.getTaskType().getValue().equals("SMALLROBOT");
    }
}
