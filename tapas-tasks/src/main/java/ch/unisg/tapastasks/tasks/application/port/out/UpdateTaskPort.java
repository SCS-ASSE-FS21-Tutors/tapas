package ch.unisg.tapastasks.tasks.application.port.out;

import ch.unisg.tapastasks.tasks.domain.Task;

import java.util.Optional;

public interface UpdateTaskPort {

    Task updateTask(Task.TaskId taskId, Task.TaskStatus status,
                    Optional<Task.ServiceProvider> provider, Optional<Task.OutputData> outputData);
}
