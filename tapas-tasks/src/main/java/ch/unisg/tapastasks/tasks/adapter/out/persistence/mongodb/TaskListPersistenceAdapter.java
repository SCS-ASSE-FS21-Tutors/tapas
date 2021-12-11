package ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb;

import ch.unisg.tapastasks.tasks.application.port.out.LoadTaskListPort;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskListPersistenceAdapter implements LoadTaskListPort {

    @Autowired
    private final TaskRepository taskRepository;

    private final TaskListMapper taskListMapper;

    @Override
    public TaskList loadTaskList(TaskList.TaskListName taskListName){
        List<MongoTaskDocument> mongoTaskDocumentList = taskRepository.findByTaskListName(taskListName.getValue());
        TaskList taskList = taskListMapper.mapToDomainEntity(mongoTaskDocumentList);
        return taskList;
    }

}
