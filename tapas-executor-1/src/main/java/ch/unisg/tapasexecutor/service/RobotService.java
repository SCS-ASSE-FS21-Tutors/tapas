package ch.unisg.tapasexecutor.service;

import ch.unisg.tapasexecutor.robot.RobotApi;
import ch.unisg.tapasexecutor.domain.Task;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class RobotService {

    @Value("${ch.tapas.executor-1.robot-api-url}")
    private String robotApiUrl;

    @Autowired
    private UpdateTaskService updateTaskService;

    public boolean isAcceptable(Task task){

        return task.getTaskType().getValue().equals("ROBOT") || task.getTaskType().getValue().equals("string");
    }

    @Async("threadPoolTaskExecutor")
    public void executeRobotTaskAsynchronously(Task task){

        if(! isAcceptable(task))
            throw new IllegalArgumentException("Task is not acceptable");

        try(var api = RobotApi.open(robotApiUrl)) {

            api.dance();
            updateTaskService.setTaskComplete(task);

        } catch (Exception exception) {

            log.warn("Exception while executing task: " + exception);
        }
    }


}
