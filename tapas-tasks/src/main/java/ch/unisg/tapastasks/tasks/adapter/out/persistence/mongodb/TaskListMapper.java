package ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb;

import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class TaskListMapper {

    private final TaskMapper taskMapper;

    public TaskListMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }
    //TODO: Make both functions with lambda streams
    TaskList mapToDomainEntity(List<MongoTaskDocument> mongoTaskDocumentList) {
        TaskList taskList = TaskList.getTapasTaskList();
        taskList.getListOfTasks().getValue().clear();
        for (MongoTaskDocument taskDocument: mongoTaskDocumentList) {
            Task task = taskMapper.mapToDomainEntity(taskDocument);
            taskList.addNewTaskToList(task);
        }
        return taskList;
    }

    List<MongoTaskDocument> mapToMongoDocuments(TaskList taskList) {
        List<MongoTaskDocument> mongoTaskDocuments = new ArrayList<>();
        for (Task task: taskList.getListOfTasks().getValue()) {
            MongoTaskDocument taskDocument = taskMapper.mapToMongoDocument(task);
            mongoTaskDocuments.add(taskDocument);
        }
        return mongoTaskDocuments;
    }

}
