package ch.unisg.tapastasks.tasks.adapter.out.persistence.mock;

import ch.unisg.tapastasks.tasks.application.port.out.AddTaskPort;
import ch.unisg.tapastasks.tasks.application.port.out.LoadTaskListPort;
import ch.unisg.tapastasks.tasks.application.port.out.LoadTaskPort;
import ch.unisg.tapastasks.tasks.application.port.out.UpdateTaskPort;
import ch.unisg.tapastasks.tasks.domain.Task;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Primary
@Profile({"local", "test"})
public class InMemoryTaskPersistenceAdapter implements
    AddTaskPort,
    LoadTaskPort,
    UpdateTaskPort, LoadTaskListPort {

    private HashMap<String, Task> inMemoryMap = new HashMap<>();

    @Override
    public void addTask(Task task) {

        inMemoryMap.put(task.getTaskId().getValue(), task);
    }

    @Override
    public List<Task> loadTaskList() {

        return inMemoryMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public Task loadTask(Task.TaskId taskId) {

        return inMemoryMap.getOrDefault(taskId.getValue(), null);
    }

    @Override
    public Task updateTask(Task.TaskId taskId, Task.TaskStatus status, Optional<Task.ServiceProvider> provider, Optional<Task.OutputData> outputData) {

        if(! inMemoryMap.containsKey(taskId.getValue()))
            throw new IllegalStateException("Task with id " + taskId.getValue() + " does not exist");

        var task = inMemoryMap.get(taskId.getValue());

        if (status != null)
            task.setTaskStatus(status);

        if (provider.isPresent())
            task.setProvider(provider.get());

        if(outputData.isPresent())
            task.setOutputData(outputData.get());

        return task;
    }
}
