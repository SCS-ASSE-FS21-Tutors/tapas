package ch.unisg.tapasexecutorpool.pool.adapter.out.persistence.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "executors")
public class MongoExecutorDocument {

    @Id
    public String executorId;

    public String executorName;
    public String executorType;
    public String executorUrl;

    public MongoExecutorDocument(String executorId, String executorName, String executorType, String executorUrl) {

        this.executorId = executorId;
        this.executorName = executorName;
        this.executorType = executorType;
        this.executorUrl = executorUrl;
    }
}
