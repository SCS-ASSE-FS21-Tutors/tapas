package ch.unisg.tapasexecutor.application.ports.in;

import ch.unisg.tapasexecutor.domain.Task;
import org.springframework.scheduling.annotation.Async;

public interface ExecuteRobotTaskUseCase {
    @Async("threadPoolTaskExecutor")
    void executeRobotTask(Task task);
}
