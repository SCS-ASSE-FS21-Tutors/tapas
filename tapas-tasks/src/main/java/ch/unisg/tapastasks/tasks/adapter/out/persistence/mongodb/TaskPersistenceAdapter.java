package ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb;

import ch.unisg.tapastasks.tasks.application.port.out.AddTaskPort;
import ch.unisg.tapastasks.tasks.application.port.out.LoadTaskListPort;
import ch.unisg.tapastasks.tasks.application.port.out.LoadTaskPort;
import ch.unisg.tapastasks.tasks.application.port.out.UpdateTaskPort;
import ch.unisg.tapastasks.tasks.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaskPersistenceAdapter implements
    AddTaskPort,
    LoadTaskPort,
    UpdateTaskPort, LoadTaskListPort {

    @Autowired
    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    @Override
    public void addTask(Task task) {
        MongoTaskDocument mongoTaskDocument = taskMapper.mapToMongoDocument(task);
        taskRepository.save(mongoTaskDocument);
    }

    @Override
    public Task loadTask(Task.TaskId taskId) {
        MongoTaskDocument mongoTaskDocument = taskRepository.findByTaskId(taskId.getValue());
        Task task = taskMapper.mapToDomainEntity(mongoTaskDocument);
        return task;
    }
    @Override
    public Task updateTask(Task.TaskId taskId, Task.TaskStatus status, Optional<Task.ServiceProvider> provider, Optional<Task.OutputData> outputData){
        MongoTaskDocument mongoTaskDocument = taskRepository.findByTaskId(taskId.getValue());
        mongoTaskDocument.setTaskStatus(status.getValue().name());
        if(provider.isPresent()){
            mongoTaskDocument.setProvider(provider.get().getValue());
        }
        if(outputData.isPresent()){
            mongoTaskDocument.setOutputData(outputData.get().getValue());
        }
        taskRepository.save(mongoTaskDocument);
        return taskMapper.mapToDomainEntity(mongoTaskDocument);
    }

    public List<Task> loadTaskList(){
        // Only retrieve the last 10 created documents for performance reasons
        Page<MongoTaskDocument> mongoTaskPage = taskRepository.findAll(PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "creationDate")));
        List<Task> taskList = new ArrayList<>();
        mongoTaskPage.forEach(mongoTaskDocument -> {
            Task task = taskMapper.mapToDomainEntity(mongoTaskDocument);
            taskList.add(task);
        });
        return taskList;
    }
}
