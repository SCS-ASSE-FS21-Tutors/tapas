package ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tasks")
public class MongoTaskDocument {
    @Id
    public String taskId;

    public String taskName;
    public String taskType;
    public String originalTaskUri;
    public String taskStatus;
    public String taskListName;

    public MongoTaskDocument(String taskId, String taskName, String taskType,
                             String originalTaskUri,
                             String taskStatus, String taskListName) {

        this.taskId = taskId;
        this.taskName = taskName;
        this.taskType = taskType;
        this.originalTaskUri = originalTaskUri;
        this.taskStatus = taskStatus;
        this.taskListName = taskListName;
    }
}
