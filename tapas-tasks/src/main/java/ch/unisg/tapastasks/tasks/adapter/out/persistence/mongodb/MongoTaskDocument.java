package ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tasks")
@AllArgsConstructor
public class MongoTaskDocument {
    @Id public String taskId;
    public String taskName;
    public String taskType;
    public String originalTaskUri;
    public String taskStatus;
    public String provider;
    public String inputData;
    public String outputData;
    public String taskListName;
}
