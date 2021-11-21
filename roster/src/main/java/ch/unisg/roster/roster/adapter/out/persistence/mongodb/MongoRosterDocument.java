package ch.unisg.roster.roster.adapter.out.persistence.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="roster")
public class MongoRosterDocument {

    @Id
    public String taskId;
    public String taskType;
    public String executorURI;

    public MongoRosterDocument(String taskId, String taskType, String executorURI){
        this.taskId = taskId;
        this.taskType = taskType;
        this.executorURI = executorURI;
    }
}
