package ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb;

import ch.unisg.tapastasks.tasks.application.port.out.AddTaskPort;
import ch.unisg.tapastasks.tasks.application.port.out.LoadTaskPort;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskPersistenceAdapter implements
    AddTaskPort,
    LoadTaskPort {

    @Autowired
    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    @Override
    public void addTask(Task task) {
        MongoTaskDocument mongoTaskDocument = taskMapper.mapToMongoDocument(task);
        taskRepository.save(mongoTaskDocument);
    }

    @Override
    public Task loadTask(Task.TaskId taskId, TaskList.TaskListName taskListName) {
        MongoTaskDocument mongoTaskDocument = taskRepository.findByTaskId(taskId.getValue(),taskListName.getValue());
        Task task = taskMapper.mapToDomainEntity(mongoTaskDocument);
        return task;
    }
}
